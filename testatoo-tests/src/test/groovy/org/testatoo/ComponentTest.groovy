package org.testatoo

import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.testatoo.config.TestatooJunitRunner
import org.testatoo.config.TestatooModules

import org.testatoo.core.component.*
import org.testatoo.core.component.input.CheckBox
import org.testatoo.core.component.input.PasswordField
import org.testatoo.core.component.input.Radio
import org.testatoo.core.component.input.TextField
import org.testatoo.core.component.list.DropDown
import org.testatoo.core.component.list.ListBox

import static org.testatoo.core.Testatoo.*
import static org.testatoo.core.property.Properties.*
import static org.testatoo.core.state.States.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(TestatooJunitRunner.class)
@TestatooModules(TestModule)
class ComponentTest {

    @BeforeClass
    public static void openTestPage() {
        open('/component.html')
    }

    @Test
    public void test_button() {
        // input type=button
        Button button = $('#button') as Button
        assertThat button is enabled
        assertThat button is visible

        assertThat button has text('Button')

        // input type=submit
        button = $('#submit') as Button
        assertThat button has text('Submit')

        // input type=reset
        button = $('#reset') as Button
        assertThat button has text('Reset')

        // button element
        button = $('#btn') as Button
        assertThat button has text('My Button Text')
    }

    @Test
    public void test_input_fields() {
        TextField textField = $('#text_field') as TextField

        assertThat textField is enabled
        assertThat textField is visible

        assertThat textField has label('Text')
        assertThat textField is empty

        textField.enter 'some value'
        assertThat textField has text('some value')
        assertThat textField has value('some value')
        assertThat textField is filled

        PasswordField passwordField = $('#password_field') as PasswordField

        assertThat passwordField is enabled
        assertThat passwordField is visible

        assertThat passwordField has label('Password')
        assertThat passwordField has text('?art')

        // Textarea


    }

    @Test
    public void test_checkbox() {
        CheckBox checkBox = $('#checkbox') as CheckBox
        assertThat checkBox is enabled
        assertThat checkBox is visible

        assertThat checkBox is unchecked
        assertThat checkBox has label('Check me out')

        check checkBox
        assertThat checkBox is checked
    }

    @Test
    public void test_radio() {
        Radio radio = $('#radio') as Radio;

        assertThat radio is enabled
        assertThat radio is visible
        assertThat radio is checked

        assertThat radio has label('Radio label');
    }

    @Test
    public void test_link() {
        Link link = $('#link') as Link
        assertThat link is enabled
        assertThat link is visible

        assertThat link has text('Link to index')
        assertThat link has reference.containing('/component.html')
    }

    @Test
    public void test_panel() {
        Panel panel = $('#panel') as Panel
        assertThat panel is enabled
        assertThat panel is visible

        assertThat panel has title.equalsTo('')
    }

    // http://en.wikipedia.org/wiki/Combo_box
    @Test
    public void test_comboBox() {

    }

    // http://en.wikipedia.org/wiki/Drop-down_list
    @Test
    public void test_dropDown() {
        DropDown dropDown = $('elements') as DropDown

        assertThat dropDown is enabled
        assertThat dropDown is visible

        assertThat dropDown has label('Elements list');

        assertThat dropDown has items.equalsTo("Helium", "Boron", "Polonium", "Calcium", "Radium")
        assertThat dropDown has items("Helium", "Boron", "Polonium", "Calcium", "Radium")

        assertThat dropDown has selectedValue('')
        dropDown select 'Polonium'
        assertThat dropDown has selectedValue('Polonium')



//        assertThat(dropdown.selectedValue(), is(""));
//        dropdown.select("Polonium");
//        assertThat(dropdown.selectedValue(), is("Polonium"));


//        assertThat(countriesList.values(), hasItems("Canada", "France", "Spain"));
//
//        // List with explicit values
//        DropDown elementsList = component(DropDown.class, "elements");
//        assertThat(elementsList.values(), hasItems("Helium", "Boron", "Polonium", "Calcium", "Radium"));
//        assertThat(elementsList.values(), hasItems("Calcium", "Boron", "Polonium", "Helium", "Radium"));
//
//        Selection<String> values = component(DropDown.class, "elements").values();
//        assertThat(values.containsAll(Arrays.asList("Helium", "Boron", "Polonium", "Calcium", "Radium")), is(true));
//    }
//
//    @Test
//    public void test_can_select_value() {
//        DropDown dropdown = component(DropDown.class, "elements");
//
//
//    }
//
//    // Test specific for Html
//    @Test
//    public void can_select_item() {
//        // List without explicit values (in this case, the value is set with the content)
//        DropDown countriesList = component(DropDown.class, "countries");
//        countriesList.select("France");
//        assertThat(countriesList.selectedValue(), is("France"));
//        countriesList.select("Spain");
//        assertThat(countriesList.selectedValue(), is("Spain"));
//
//        // List with explicit values
//        DropDown elementsList = component(DropDown.class, "elements");
//        elementsList.select("Polonium");
//        assertThat(elementsList.selectedValue(), is("Polonium"));
//        elementsList.select("Calcium");
//        assertThat(elementsList.selectedValue(), is("Calcium"));
//    }
//
//    @Test
//    public void test_label() {
//        assertThat(component(DropDown.class, "elements"), has(label("Elements list")));
//    }
//


    }

//    http://en.wikipedia.org/wiki/List_box
    @Test
    public void test_listBox() {
        ListBox listBox = $('cities') as ListBox

    }

//    @Test
//    public void test_list() {
//        List list = $('#list_view') as List
//
//        assert list.is(filled);
//
////        assert list.contains(item1, item2);
//
////        assert list.has(5.items);
//        assert list.has(size.equalsTo(5));
//        assert list.items().size == 5;
//
//
////        assert list.has(values(['Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5']))
//    }
//
//    @Test
//    public void test_contains() {
//
//    }

    // ===================================================

//

//    @Test
//    public void test_page() {
//        open('/component.html');
//
//        assertThat(page().has(title()).equalsTo('Testatoo Rocks'));
//    }
//
//    @Test
//    public void test_contains() {
//        open('/component.html');
//
//        assertThat(page().contains(
//                new Button('button'),
//                new TextField('text_field'),
//                new Radio('radio')
//        ));
//
//        Panel panel = new Panel('panel');
//        assertThat(panel.contains(
//                new Button('button_in_visible_panel'),
//                new Button('invisible_button_in_visible_panel')
//        ));
//
//        Panel invisible_panel = new Panel('invisible_panel');
//        try {
//            assertThat(invisible_panel.contains(
//                    new Button('button_in_visible_panel')
//            ));
//        } catch (AssertionError e) {
//            assertEquals('Component Panel with id: \'invisible_panel\' does no contains component Button with id: \'button_in_visible_panel\'', e.getMessage());
//        }
//    }
//
//    @Test
//    public void test_displays() {
//        open('/component.html');
//
//        assertThat(page().displays(
//                new Button('button'),
//                new TextField('text_field'),
//                new Radio('radio')
//        ));
//
//        Panel panel = new Panel('panel');
//        assertThat(panel.displays(
//                new Button('button_in_visible_panel')
//        ));
//
//        try {
//            assertThat(panel.displays(
//                    new Button('button_in_visible_panel'),
//                    new Button('invisible_button_in_visible_panel')
//            ));
//        } catch (AssertionError e) {
//            assertEquals('Component Panel with id: \'panel\' does no displays component Button with id: \'invisible_button_in_visible_panel\'', e.getMessage());
//        }
//    }
}
