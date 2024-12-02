import PropTypes from "prop-types";

function Button(props) {
    return(
        <button className={`${props.color} py-2 px-8 rounded-xl text-white`}>
            {props.icon ?? null} {props.text}
        </button>
    );

}

Button.propTypes = {
    text: PropTypes.string.isRequired,
    color: PropTypes.string,
    icon: PropTypes.object,
}

Button.defaultProps = {
    icon: null,
    text: "Click Me",
    color: "bg-cyan-400"
}

export default Button