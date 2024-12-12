import PropTypes from "prop-types";

function Notification({text, onDelete, type}) {
    const deleteIcon = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                            stroke="currentColor" className="size-5">
        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"/>
    </svg>
    let color;
    if (type === "success") {
        color = "bg-green-500";
    } else if (type === "error") {
        color = "bg-red-500";
    }


    return(
        <div className="fixed bottom-2 right-2">
            <div className={`flex space-between items-center p-4 text-white text-lg gap-x-5 ${color}`}>
                <p>{text}</p>
                <span className="cursor-pointer" onClick={onDelete}>{deleteIcon}</span>
            </div>
        </div>
    );
}

Notification.propTypes = {
    text: PropTypes.string,
    onDelete: PropTypes.func,
    type: PropTypes.string,
}

export default Notification