package login.unit

import login.Factory
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import static org.testatoo.core.Testatoo.open

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class LoginPageTest {

    @Delegate
    private static Factory factory = new Factory()

    @BeforeClass
    public static void open() {
        open('/login/index.html')
    }

    @Test
    public void page_contains_expected_elements() {
//        assert login_panel.is(visible)

//        assertThat login_button is visible

//        assert login_panel.has(title.equalsTo('Login Form'))

//        assert login_panel.contains(email_field, password_field, login_button)

//        assert email_field.has(label.equalsTo('Email'))
//        assert email_field.has(placeholder.equalsTo('joe@blow.org'))

//        assert password_field.has(label.equalsTo('Password'))
//
//        assert login_button.has(text.equalsTo('Login'))
    }

}
