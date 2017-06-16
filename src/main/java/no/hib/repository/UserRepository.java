package no.hib.repository;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import no.hib.models.AdminUser;
import no.hib.models.BankIdUser;
import no.hib.utils.SearchStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private CloudantClient client;
    private Database database;

    public UserRepository(){
        client = ClientBuilder.account("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix:91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a@58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .username("58761b32-3b76-417d-a889-6c2bd1a2a425-bluemix")
                .password("91de7276fb3f1509f7731d93b5b1caf8c7ec401b578cda83b224dcc47278c09a")
                .build();

        database = client.database("msservice",true);
    }

    public AdminUser getAdminUser(String email) {
        String searchString = SearchStringGenerator.getSearchString("email", "eq", email);
        List<AdminUser> users = database.findByIndex(searchString, AdminUser.class);
        if(users.size() == 0) return null;
        return users.get(0);
    }

    public BankIdUser getBankIdUser(String ssn){
        String searchString = SearchStringGenerator.getSearchString("ssn", "eq", ssn, "password","exists","true");
        List<BankIdUser> users = database.findByIndex(searchString, BankIdUser.class);
        if(users.size() == 0) return null;
        return users.get(0);
    }

    public void createBankIdUser(String ssn, String password) {
        BankIdUser bankIdUser = new BankIdUser(ssn, password);
        database.save(bankIdUser);
    }

    public void deleteBankIdUser(String ssn) {
        database.remove(getBankIdUser(ssn));
    }
}
