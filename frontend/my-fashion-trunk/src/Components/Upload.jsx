import Button from "./Elements/Button.jsx";

function Upload() {

    const closeIcon = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                           stroke="currentColor" className="size-6">
        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"/>
    </svg>

    // TODO: Continue working on this component

    return (
        <div className="modal p-3">
            <div className="flex justify-between">
                <h1 className="text-gray-800 font-semibold text-xl">Add New Listing</h1>
                <span>{closeIcon}</span>
            </div>
            <hr className="my-3"/>
            <div>
                <form>
                    <div>
                        <label className="form-label">Title:</label>
                        <input name="title" type="text" className="form-input w-full"/>
                    </div>
                    <div className="w-80 h-60 bg-gray-400 rounded my-6">
                        Drag image or Browse image from computer
                    </div>
                    <div>
                        <Button text="Submit"/>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Upload