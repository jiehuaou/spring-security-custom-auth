# Spring Security Architecture

![Security info](./images/Security.jpg "Security")

## process step

* request comes in with credential 
    * credential can be user name and password combinations, tickets, or public key cert.
* **Authentication Filter** transfer credential to **Authentication Manager**
* **Authentication Manager** transfer credential to **Authentication Provider**
* **Authentication Provider** ask the actual **User detail service** to authenticate the request
* finally put the **Authentication Token** into Security Context

## sample WebSecurityConfigurerAdapter

```java
@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }

     @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .anonymous().principal("guest").authorities("ROLE_GUEST") // give name to user without login
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/course").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/student").hasAnyRole( "ADMIN", "USER")
                .antMatchers("/hello").permitAll()  // login or not
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
```

## user/role

* admin - ADMIN
* user - USER
* student - USER
* abc - ROLE_USER, ROLE_ADMIN
* anonymous(id "guest") - ROLE_GUEST

## resource role

* POST:/course - ADMIN
* POST:/student - ADMIN, USER
* any:/hello - login or not
* other - must login

## test case

```shell
curl -X POST  http://127.0.0.1:8080/student -u "user:123"

curl -X POST  http://127.0.0.1:8080/student -u "admin:123"

curl -X POST  http://127.0.0.1:8080/course -u "user:123"

curl -X POST  http://127.0.0.1:8080/course -u "admin:123"

curl -X POST  http://127.0.0.1:8080/hello 

curl -X POST  http://127.0.0.1:8080/other -u "abc:xxxxxx"

curl -X POST  http://127.0.0.1:8080/other
```
