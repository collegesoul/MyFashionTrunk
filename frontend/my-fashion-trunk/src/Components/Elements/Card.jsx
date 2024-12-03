import PropTypes from "prop-types";

function Card({status = "Accepted", title = "Title here"}) {

    let statusColour;
    switch (status) {
        case "Accepted":
            statusColour = "bg-green-600";
            break;
        case "Rejected":
            statusColour = "bg-red-600";
            break;
        case "Pending":
            statusColour = "bg-yellow-500";
            break;
        default:
            statusColour = "bg-gray-500";
    }

    return(
        <div className="card">
            <h2>{title}</h2>
            <div className="bg-amber-700 w-auto h-44 my-4"></div>
            <div>
                <span className={`${statusColour} h-3 w-3 mr-2 rounded-full inline-block`}></span>
                <span>Status: {status}</span>
            </div>
        </div>
    );
}

Card.propTypes = {
    status: PropTypes.string,
    title: PropTypes.string,
}

export default Card