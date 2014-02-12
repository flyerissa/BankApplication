import com.luxoft.bankapp.domain.bank.Account;

/**
 * Created by User on 12.02.14.
 */
public abstract class AbstractAccount implements Account{
    @Override
    public void deposit(float x) {

    }

    @Override
    public void withdraw(float x) {

    }

    @Override
    public float maximumAmountToWithdraw() {
        return 0;
    }
}
