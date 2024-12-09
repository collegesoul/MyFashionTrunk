import Button from "./Elements/Button.jsx";
import PropTypes from "prop-types";

/**
 * A dropdown component for filtering listings by status
 * @param {string} selected the currently selected filter option
 * @param {function} onCheck the function to call when a filter option is selected
 * @param {function} onReset the function to call when the reset button is clicked
 * @returns {JSX.Element} the rendered filter dropdown component
 * **/
function FilterDropDown({selected, onCheck, onReset}) {
    return(
        <div className="modal p-3 right-28">
            <form>
                <div>
                    <input
                        className="accent-gray-600 w-3.5 h-3.5"
                        type="radio"
                        name="filter"
                        id="accepted"
                        checked={selected === "accepted"}
                        onChange={()=>onCheck("accepted")}
                    />
                    <label className="pl-1" htmlFor="accepted">Accepted</label>
                </div>
                <div>
                    <input
                        className="accent-gray-600 w-3.5 h-3.5"
                        type="radio"
                        name="filter"
                        id="rejected"
                        checked={selected === "rejected"}
                        onChange={()=>onCheck("rejected")}
                    />
                    <label className="pl-1" htmlFor="rejected">Rejected</label>
                </div>
                <div className="mt-2">
                    <Button text="Reset"
                            color="bg-gray-500"
                            style="hover:bg-gray-600"
                            clickEvent={onReset}
                    />
                </div>
            </form>
        </div>
    );
}

FilterDropDown.propTypes = {
    selected: PropTypes.string,
    onCheck: PropTypes.func,
    onReset: PropTypes.func,
}

export default FilterDropDown