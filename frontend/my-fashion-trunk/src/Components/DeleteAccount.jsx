import Button from "./Elements/Button.jsx";

function DeleteAccount() {
    const closeIcon = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                           stroke="currentColor" className="size-6">
        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"/>
    </svg>

    return(
        <div>
            <div>
                <h2>Confirm delete account</h2>
                <span>{closeIcon}</span>
            </div>
            <hr/>
            <div>
                <p>Are you sure you want to delete your account? Type
                    <span><i>Value</i></span> to confirm delete.
                </p>
                <div>
                    <input type="text"/>
                </div>
                <div>
                    <Button text="Confirm delete" color="bg-red-400"/>
                </div>
            </div>
        </div>
    );
}

export default DeleteAccount