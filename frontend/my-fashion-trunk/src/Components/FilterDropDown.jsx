import Button from "./Elements/Button.jsx";

function FilterDropDown() {
    return(
        <div className="modal p-3 right-28">
            <div>
                <input className="accent-gray-600" type="checkbox" name="accepted"/>
                <label className="pl-1">Accepted</label>
            </div>
            <div>
                <input className="accent-gray-600" type="checkbox" name="pending"/>
                <label className="pl-1">Pending</label>
            </div>
            <div>
                <input className="accent-gray-600" type="checkbox" name="rejected"/>
                <label className="pl-1">Rejected</label>
            </div>
            <div className="mt-2">
                <Button text="Reset" color="bg-gray-500" style="hover:bg-gray-600"/>
            </div>
        </div>
    );
}

export default FilterDropDown