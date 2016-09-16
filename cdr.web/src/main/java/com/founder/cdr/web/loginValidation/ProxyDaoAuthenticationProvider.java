package com.founder.cdr.web.loginValidation;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Used for dealing with somethings before user login
 * @author jin_peng
 * @version 1.0, 2012/07/17
 */
public class ProxyDaoAuthenticationProvider extends DaoAuthenticationProvider
{

    /**
     * 进行密码修改验证
     * @param authentication 校验管理对象
     * @return 返回检验成功authentication
     */
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        LoginUserDetails userDtails = null;
        boolean isValidationPassed = true;

        try
        {
            // To validate login
            String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                    : authentication.getName();

            userDtails = (LoginUserDetails) this.retrieveUser(username,
                    (UsernamePasswordAuthenticationToken) authentication);

            this.additionalAuthenticationChecks(userDtails,
                    (UsernamePasswordAuthenticationToken) authentication);
        }
        catch (BadCredentialsException bcx)
        {
            // To be not success
            isValidationPassed = false;
        }

        try
        {
            // Resetting password or not
            if (userDtails != null && isValidationPassed)
            {
                if (userDtails.isNeedAlterPw())
                {
                    throw new UsernameNotFoundException(
                            "User needed for altering password");
                }
            }
        }
        catch (DataAccessException repositoryProblem)
        {
            throw new AuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        return super.authenticate(authentication);
    }

}
