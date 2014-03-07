package bootstrap

import org.testatoo.core.component.Component
import org.testatoo.core.component.Panel
import org.testatoo.core.property.*
import org.testatoo.core.state.Selected
import org.testatoo.core.state.UnSelected

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class Accordion extends Component {

    Accordion() {
        support Size, {
            Component c -> Integer.valueOf(c.evaluator.getString("\$('#${id} .panel-heading a').length"))
        }
        support Items, {
            Component c -> c.evaluator.getMetaInfo("\$('#${id} .panel-heading a')").collect { it as Item }
        }
    }

    List<Item> getItems() {
        this.evaluator.getMetaInfo("\$('#${id} .panel-heading a')").collect { it as Item }
    }

    private class Item extends Component {

        Item() {
            support Title, {
                Component c -> c.evaluator.getString("\$('#${id}').text()").trim()
            }
            support Selected, {
                Component c ->  Boolean.valueOf(c.evaluator.getString("\$('#${panel.id}').hasClass('in')"))
            }
            support UnSelected, {
                Component c ->  !Boolean.valueOf(c.evaluator.getString("\$('#${panel.id}').hasClass('in')"))
            }
        }

        Panel getPanel() {
            return $('#' + this.evaluator.getString("\$('#${id}').attr('href').substring(1)")) as Panel
        }
    }

}
