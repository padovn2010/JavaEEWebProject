package com.duongpt.database;

import com.duongpt.model.Account;
import javax.enterprise.context.RequestScoped;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Pado
 * This class uses EntityManager that is used to persist Entities to a DB
 * Entity is only POJO
 */
@Named
@RequestScoped
public class Transactions {
    
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String dob;
    
    @PersistenceContext
    private EntityManager  entityManager;
    
    @Resource
    private UserTransaction userTransaction;
    
    @SuppressWarnings("CallToPrintStackTrace")
    public String addAccount(){
        
        String retVal = "confirmation";
        
        Account account = new Account();
        
        account.setuserName(userName);
        account.setpassword(password);
        account.setFullName(email);
        account.setEmail(fullName);
        account.setDob(dob);
        
        try {
            userTransaction.begin();
            entityManager.persist(account);
            userTransaction.commit();
            
        } catch (HeuristicMixedException |
                HeuristicRollbackException |
                IllegalStateException |
                NotSupportedException |
                RollbackException |
                SecurityException |
                SystemException e)
        {
            //To see with type of throwed exception
            System.err.println("Exception type: " + e.getMessage() );
            
            if (e instanceof RollbackException){
                retVal = "errortransaction";
                e.printStackTrace();
            } else {
                retVal = "errorpage";
                e.printStackTrace();
            }
        }
        return retVal;
    }

    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getDob() {
        return dob;
    }
    
    public void setDob(String dob) {
        this.dob = dob;
    }
    
}

