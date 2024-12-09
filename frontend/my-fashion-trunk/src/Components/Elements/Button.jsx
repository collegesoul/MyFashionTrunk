import PropTypes from "prop-types";

/**
 * A customizable button component
 * @param {string} text the text to display on the button
 * @param {Object} icon the icon to display on the button, null by default
 * @param {string} type the html button type: submit | button | reset
 * @param {string} color the colour of the button, uses tailwind cyan colour by default
 * @param {string} style additional tailwind classes for styling
 * @param {function} clickEvent the function to call when button is clicked
 * @returns {JSX.Element} the rendered button component
 * **/
function Button({
                    text,
                    icon=null,
                    type="button",
                    color="bg-cyan-400",
                    style="",
                    clickEvent = null,
}) {
    return(
        <button type={type}
                onClick={clickEvent}
                className={`${color} py-2 px-8 text-white font-medium ${style}`}
        >
            {icon ?? null} {text}
        </button>
    );

}

Button.propTypes = {
    text: PropTypes.string.isRequired,
    type: PropTypes.string,
    color: PropTypes.string,
    icon: PropTypes.object,
    style: PropTypes.string,
    clickEvent: PropTypes.func,
}

export default Button