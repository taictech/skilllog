package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * フォームログインの最小構成を担う Spring Security 設定です。
 *
 * <p>Thymeleaf ベースの画面遷移を前提に、Spring Security 6 の DSL で認可・認証・CSRF
 * （クロスサイトリクエストフォージェリ）対策を順番に設定します。
 * <p>{@code /login} と静的リソース（/css/**, /js/**, /images/**）のみを {@code permitAll} とし、
 * それ以外の URL はすべて認証必須とすることで最低限の防御線を築きます。
 * <p>ログイン成功時は {@code /}（HomeController が /skilllog-web へリダイレクト）、
 * 失敗時は {@code /login?error}、ログアウト後は {@code /login?logout} へ遷移させて UX を一定に保ちます。
 * <p>CSRF トークンは Thymeleaf で hidden field に埋め込み、欠落時は 403 となる点を明記しておきます。
 * <p>インメモリユーザはデモ用途の暫定実装であり、本番では永続ストアへの差し替えが必要です。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Spring Security のフィルタ連鎖を構築します。
     *
     * <p>{@code authorizeHttpRequests} で {@code /login} と静的リソースを公開し、
     * {@code anyRequest().authenticated()} で残りの URL を認証必須にするのが最初のステップです。
     * <p>{@code formLogin} ではカスタムログイン画面（GET /login）を利用し、成功時 {@code /}、
     * 失敗時 {@code /login?error} へ自動遷移させることで Controller 側の条件分岐を排除します。
     * <p>{@code logout} は POST /logout を必須にし、成功後 {@code /login?logout} でフィードバックを出します。
     * <p>{@code csrf} は既定値のまま有効にし、フォームが hidden トークンを欠落させると 403 になる点を押さえます。
     *
     * @param http HttpSecurity DSL
     * @return 構築済み SecurityFilterChain
     * @throws Exception DSL 構築失敗時
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            )
            .csrf(csrf -> {
                // Default CSRF protection stays enabled.
                // csrf.ignoringRequestMatchers("/h2-console/**"); // Example for H2 console access.
            });
        return http.build();
    }

    /**
     * {@link BCryptPasswordEncoder} を返すパスワードエンコーダーです。
     *
     * <p>BCrypt はソルト付きで強度が高く、Spring Security の推奨方式に合致します。
     * <p>ハードコードされたパスワードをエンコードするのはデモ用に限定し、本番では外部設定を利用してください。
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * デモ用のインメモリユーザを提供します。
     *
     * <p>{@code user/password} に ROLE_USER を付与し、フォームログインの動作確認だけに集中できるようにします。
     * <p>責務は「ユーザ情報の仮置き」であり、実サービスでは DB や LDAP に実装を差し替える前提です。
     */
    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    /*
     * // Spring Boot 2.7.x / Spring Security 5.x example using WebSecurityConfigurerAdapter:
     * @Configuration
     * @EnableWebSecurity
     * public class SecurityConfig extends WebSecurityConfigurerAdapter {
     *     @Override
     *     protected void configure(HttpSecurity http) throws Exception {
     *         http
     *             .authorizeRequests()
     *                 .antMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
     *                 .anyRequest().authenticated()
     *             .and()
     *             .formLogin()
     *                 .loginPage("/login").permitAll()
     *                 .defaultSuccessUrl("/", true)
     *                 .failureUrl("/login?error")
     *             .and()
     *             .logout()
     *                 .logoutUrl("/logout")
     *                 .logoutSuccessUrl("/login?logout");
     *     }
     * }
     */
}
