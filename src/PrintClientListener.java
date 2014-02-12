import com.luxoft.bankapp.domain.bank.Client;

/**
 * Created by User on 12.02.14.
 */
public class PrintClientListener implements ClientRegistrationListener {
    @Override
    public void onClientAdded(Client c) {
        System.out.println(c);
    }
}
