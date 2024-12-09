package com.ccsw.tutorial_loan.loan;

import com.ccsw.tutorial_loan.client.ClientClient;
import com.ccsw.tutorial_loan.client.model.ClientDto;
import com.ccsw.tutorial_loan.common.criteria.SearchCriteria;
import com.ccsw.tutorial_loan.game.GameClient;
import com.ccsw.tutorial_loan.game.model.GameDto;
import com.ccsw.tutorial_loan.loan.model.Loan;
import com.ccsw.tutorial_loan.loan.model.LoanDto;
import com.ccsw.tutorial_loan.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientClient clientClient;

    @Autowired
    GameClient gameClient;

    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto, String titleGame, String clientName, Date date) {
        Specification<Loan> spec = Specification.where(null);

        if (clientName != null && !clientName.isEmpty()) {
            SearchCriteria clientCriteria = new SearchCriteria("clientId", ":", clientName);
            LoanSpecifications clientSpec = new LoanSpecifications(clientCriteria);
            spec = spec.and(clientSpec);
        }

        if (titleGame != null && !titleGame.isEmpty()) {
            SearchCriteria gameCriteria = new SearchCriteria("gameId", ":", titleGame);
            LoanSpecifications gameSpec = new LoanSpecifications(gameCriteria);
            spec = spec.and(gameSpec);
        }

        if (date != null) {
            SearchCriteria startDateCriteria = new SearchCriteria("startDate", "<=", date);
            LoanSpecifications startDateSpec = new LoanSpecifications(startDateCriteria);

            SearchCriteria endDateCriteria = new SearchCriteria("endDate", ">=", date);
            LoanSpecifications endDateSpec = new LoanSpecifications(endDateCriteria);

            spec = spec.and(startDateSpec).and(endDateSpec);
        }

        Pageable pageable = dto.getPageable().getPageable();
        return loanRepository.findAll(spec, pageable);
    }

    @Override
    public void save(LoanDto dto) throws Exception {
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        Date startDateAsDate = convertToDate(startDate);
        Date endDateAsDate = convertToDate(endDate);

        if (endDate.isBefore(startDate)) {
            throw new Exception("La fecha final del préstamo tiene que ser mayor que la de inicio");
        }

        if (startDate.plusDays(14).isBefore(endDate)) {
            throw new Exception("El préstamo no puede superar los 14 días");
        }

        int loanCountByClient = loanRepository.loanCountByClient(dto.getClient().getId(), startDateAsDate, endDateAsDate);
        if (loanCountByClient >= 1) {
            throw new Exception("El cliente ha alcanzado el tope máximo de préstamos en las fechas seleccionadas");
        }

        boolean isGameLoaned = loanRepository.isGameLoaned(dto.getGame().getId(), startDateAsDate, endDateAsDate);
        if (isGameLoaned) {
            throw new Exception("Este juego ya ha estado prestado en las fechas seleccionadas");
        }

        // Obtener cliente y juego mediante Feign Clients
        ClientDto clientDto = findClientById(dto.getClient().getId());
        GameDto gameDto = findGameById(dto.getGame().getId());

        Loan loan = new Loan();
        loan.setId(dto.getId());
        loan.setClientId(dto.getClient().getId());
        loan.setGameId(dto.getGame().getId());
        loan.setStartDate(startDateAsDate);
        loan.setEndDate(endDateAsDate);

        loanRepository.save(loan);
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private ClientDto findClientById(Long id) {
        try {
            return clientClient.findClientById(id);
        } catch (Exception e) {
            // Alternativa: buscar en `findAll` si `findClientById` falla
            return clientClient.findAll().stream()
                    .filter(client -> client.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        }
    }

    private GameDto findGameById(Long id) {
        try {
            return gameClient.findGameById(id);
        } catch (Exception e) {
            // Alternativa: buscar en `findAll` si `findGameById` falla
            return gameClient.findAll().stream()
                    .filter(game -> game.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Juego no encontrado"));
        }
    }

    @Override
    public void delete(Long id) {
        this.loanRepository.deleteById(id);
    }

    @Override
    public Page<LoanDto> findPage(LoanSearchDto dto) {
        return null;
    }

    @Override
    public Page<LoanDto> findLoansFiltered(LoanSearchDto dto) {
        return null;
    }

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    @Override
    public void save(Long id, LoanDto loanDto) {
        // Método vacío por ahora
    }
}