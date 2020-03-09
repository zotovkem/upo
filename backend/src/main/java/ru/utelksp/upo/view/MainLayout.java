package ru.utelksp.upo.view;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.utelksp.upo.UpoErrorHandler;
import ru.utelksp.upo.service.SecurityService;
import ru.utelksp.upo.view.component.Menu;
import ru.utelksp.upo.view.crud.*;
import ru.utelksp.upo.view.report.CertificateCrossReportView;
import ru.utelksp.upo.view.report.CertificateReportView;
import ru.utelksp.upo.view.report.ProgramReportView;

import javax.annotation.PostConstruct;

/**
 * Главная страница приложения.
 */
@HtmlImport("css/shared-styles.html")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PWA(name = "Реестр програмного обеспечения", shortName = "UPO", startPath = "login",
        manifestPath = "manifest.json", offlinePath = "offline-page.html", enableInstallPrompt = false)
@RequiredArgsConstructor
public class MainLayout extends FlexLayout implements RouterLayout {
    private Menu menu;
    @Value("${server.servlet.context-path}")
    private String appUrl;
    private final ApplicationEventPublisher eventPublisher;
    private final SecurityService securityService;
    private final UpoErrorHandler errorHandler;

    /**
     * Инициализация меню
     */
    @PostConstruct
    private void init() {
        VaadinSession.getCurrent().setErrorHandler(errorHandler);
        setSizeFull();
        setClassName("main-layout");

        menu = new Menu(appUrl, eventPublisher, securityService);
        menu.init();

        addViewUser();
        addViewAdmin();
        menu.addView(CertificateReportView.class, CertificateReportView.VIEW_NAME, VaadinIcon.SPLINE_CHART.create());
        menu.addView(ProgramReportView.class, ProgramReportView.VIEW_NAME, VaadinIcon.NEWSPAPER.create());
        menu.addView(CertificateCrossReportView.class, CertificateCrossReportView.VIEW_NAME, VaadinIcon.ABSOLUTE_POSITION.create());
        add(menu);
    }

    /**
     * Пункты меню доступные пользователю
     */
    private void addViewUser() {
        if (hasPermission("USER")) {
            menu.addView(ProgramCrudView.class, ProgramCrudView.VIEW_NAME, VaadinIcon.BROWSER.create(), null);
            menu.addView(CertificateCrudView.class, CertificateCrudView.VIEW_NAME, VaadinIcon.DIPLOMA.create());
            menu.addView(OrderCrudView.class, OrderCrudView.VIEW_NAME, VaadinIcon.EDIT.create());
            menu.addView(EmployeeCrudView.class, EmployeeCrudView.VIEW_NAME, VaadinIcon.USER.create());
            menu.addView(ComputerCrudView.class, ComputerCrudView.VIEW_NAME, VaadinIcon.DESKTOP.create());
            menu.addView(OrganizationCrudView.class, OrganizationCrudView.VIEW_NAME, VaadinIcon.BUILDING.create());
            menu.addView(TypeUsingCrudView.class, TypeUsingCrudView.VIEW_NAME, VaadinIcon.ACCORDION_MENU.create());
        }
    }

    /**
     * Пункты меню доступные только Администратору
     */
    private void addViewAdmin() {
        if (hasPermission("ADMIN")) {
            menu.addView(UserCrudView.class, UserCrudView.VIEW_NAME, VaadinIcon.USER_CARD.create());
            menu.addView(JournalEventCrudView.class, JournalEventCrudView.VIEW_NAME, VaadinIcon.TASKS.create());
        }
    }

    /**
     * Проверка роли пользователя
     *
     * @param authority роль пользователя
     */
    private Boolean hasPermission(String authority) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals(authority));
    }
}
