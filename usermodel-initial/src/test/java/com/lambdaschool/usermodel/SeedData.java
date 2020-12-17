package com.lambdaschool.usermodel;


import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.RoleService;
import com.lambdaschool.usermodel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Transactional
@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Transactional
    @Override
    public void run(String[] args) throws Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        User u1 = new User("testadmin","password","admin@gmail.local");
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));
        u1.getRoles().add(new UserRoles(u1, r3));
        u1.getUseremails().add(new Useremail(u1,"admin@gmail.local"));
        u1.getUseremails().add(new Useremail(u1,"admin@gmail.local"));

        userService.save(u1);

        // data, user
        User u2 = new User("testcinnamon","1234567","cinnamon@gmail.local");
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));
        u2.getUseremails().add(new Useremail(u2,"cinnamon2@gmail.local"));
        u2.getUseremails().add(new Useremail(u2,"cinnamon3@gmail.local"));
        u2.getUseremails().add(new Useremail(u2,"cinnamon4@gmail.local"));
        userService.save(u2);

        // user
        User u3 = new User("testbarnbarn", "ILuvM4th!", "barnbarn@gmail.local");
        u3.getRoles().add(new UserRoles(u3, r2));
        u3.getUseremails().add(new Useremail(u3,"barnbarn2@gmail.local"));
        userService.save(u3);

        User u4 = new User("testputtat","password","puttat@gmail.local");
        u4.getRoles().add(new UserRoles(u4, r2));
        userService.save(u4);

        User u5 = new User("testmisskitty","password","misskitty@gmail.local");
        u5.getRoles().add(new UserRoles(u5, r2));
        userService.save(u5);

        if (false)
        {
            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                    new RandomService());
            Faker nameFaker = new Faker(new Locale("en-US"));

            for (int i = 0; i < 25; i++)
            {
                new User();
                User fakeUser;

                fakeUser = new User(nameFaker.name()
                        .username(),
                        "password",
                        nameFaker.internet()
                                .emailAddress());
                fakeUser.getRoles()
                        .add(new UserRoles(fakeUser, r2));
                fakeUser.getUseremails()
                        .add(new Useremail(fakeUser,
                                fakeValuesService.bothify("????##@gmail.com")));
                userService.save(fakeUser);
            }
        }
    }
}
