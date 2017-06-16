package no.hib.logic;

import no.hib.logic.interfaces.LoginService;
import no.hib.models.AdminUser;
import no.hib.models.BankIdUser;
import no.hib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean validUser(BankIdUser bankIdUser) {
        BankIdUser savedUser = userRepository.getBankIdUser(bankIdUser.getSsn());
        if (savedUser != null && savedUser.getPassword().equals(bankIdUser.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validUser(AdminUser adminUser) {
        AdminUser savedUser = userRepository.getAdminUser(adminUser.getEmail());
        if (savedUser != null && savedUser.getPassword().equals(adminUser.getPassword())) {
            return true;
        }
        return false;
    }
}
