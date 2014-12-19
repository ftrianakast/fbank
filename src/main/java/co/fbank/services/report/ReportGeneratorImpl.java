package co.fbank.services.report;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.fbank.domain.Account;
import co.fbank.domain.Client;
import co.fbank.domain.Movement;
import co.fbank.domain.MovementType;
import co.fbank.persistence.ClientRepository;
import co.fbank.services.utils.DateManipulation;

@Service
public class ReportGeneratorImpl implements ReportGenerator {
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private DateManipulation dateManipulation;

	@Override
	public Report generateAccountsReport(Date initDate, Date endDate,
			Long clientId) {
		Client client = clientRepository.findOne(clientId);
		List<Account> clientAccounts = client.getAccounts();
		Report report = new Report();

		List<AccountInfo> accountsReport = clientAccounts.stream()
				.map(account -> produceAccountInfo(account, initDate, endDate))
				.collect(Collectors.toList());

		report.setInitDate(initDate);
		report.setEndDate(endDate);
		report.setAccounts(accountsReport);

		return report;
	}

	/**
	 * Produce the info of a specific account
	 * 
	 * @param account
	 * @param initDate
	 * @param endDate
	 * @return
	 */
	private AccountInfo produceAccountInfo(Account account, Date initDate,
			Date endDate) {
		AccountInfo accountReport = new AccountInfo();
		List<MovementInfo> creditMovements = produceMovementsSummary(account,
				MovementType.CREDIT, initDate, endDate);
		List<MovementInfo> debitMovements = produceMovementsSummary(account,
				MovementType.DEBIT, initDate, endDate);

		accountReport.setAccountNumber(account.getNumber());
		accountReport.setCreditMovements(creditMovements);
		accountReport.setDebitMovements(debitMovements);
		accountReport.setTotalCreditMovements(accountReport
				.getCreditMovements().size());
		accountReport.setTotalDebitMovements(accountReport.getDebitMovements()
				.size());

		return accountReport;
	}

	/**
	 * Produce the movements summary of an account
	 * 
	 * @param account
	 * @param movementType
	 * @param initDate
	 * @param endDate
	 * @return
	 */
	private List<MovementInfo> produceMovementsSummary(Account account,
			MovementType movementType, Date initDate, Date endDate) {
		List<MovementInfo> movementSummary = account
				.getMovements()
				.stream()
				.filter(mvm -> mvm.getType().equals(movementType)
						&& dateManipulation.isDateBetween(mvm.getDate(),
								initDate, endDate))
				.map(creditMvm -> produceMovementInfo(creditMvm))
				.collect(Collectors.toList());
		return movementSummary;
	}

	/**
	 * Produce the specific info of a movement
	 * 
	 * @param movement
	 * @return
	 */
	private MovementInfo produceMovementInfo(Movement movement) {
		MovementInfo movementReport = new MovementInfo(movement.getValue(),
				movement.getDate(), movement.getAccount().getBalance());
		return movementReport;
	}
}
