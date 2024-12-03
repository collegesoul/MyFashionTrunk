import PropTypes from "prop-types";

function Button({
                    icon=null,
                    type="button",
                    text="Click Me",
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