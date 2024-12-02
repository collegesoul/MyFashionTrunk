import Button from "./Elements/Button.jsx";

function Upload() {
    return(
        <div>
            <form>
                <label>Title</label>
                <div>
                    <input type="text"/>
                </div>
                <div>
                    Drag image or Browse image from computer
                </div>
                <div>
                    <Button text="Submit"/>
                </div>
            </form>
        </div>
    );
}

export default Upload