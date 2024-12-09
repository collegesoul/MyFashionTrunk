import PropTypes from "prop-types";

/**
 * A category component that displays the name of the category and optional delete icon
 * @param {function} clickEvent the function called when the delete Icon is clicked
 * @param {string} name the name of the category displayed
 * @param {boolean} canDelete whether to render the delete Icon if the category can be deleted
 * @returns {JSX.Element} the rendered category component
 * **/
function Category ({clickEvent, name, canDelete = true}) {
    const deleteIcon = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                           stroke="currentColor" className="size-5">
        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"/>
    </svg>

    let deleteStyle = "block";
    if (!canDelete) {
        deleteStyle = "none";
    }


    return(
        <div className="flex space-between py-1 px-2 gap-x-3 rounded-2xl items-center bg-amber-200 w-fit shadow-md">
            <p>{name}</p>
            <span onClick={clickEvent}
                  className="cursor-pointer text-red-600"
                  style={{display: deleteStyle}}>
                {deleteIcon}
            </span>
        </div>
    );
}
Category.propTypes = {
    name: PropTypes.string,
    clickEvent: PropTypes.func,
    canDelete: PropTypes.bool,
}

export default Category