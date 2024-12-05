import Button from "./Elements/Button.jsx";
import Category from "./Elements/Category.jsx";

function Categories() {
    return(
        <div>
            <h1 className="heading">Categories</h1>
            <hr className="my-4 lg:w-8/12"/>
            <div className="flex">
                <input className="p-2 border-2 rounded-l w-7/12"
                       type="text" placeholder="Add New Category"/>
                <select className="border-y-2 p-1">
                    <option value="Allowed" selected={true}>Allowed</option>
                    <option value="Prohibited">Prohibited</option>
                </select>
                <Button text="Add"/>
            </div>
            <div className="grid grid-cols-2 gap-x-6 lg:gap-x-16 mt-6">
                <div className="flex-col">
                    <h2 className="text-center font-semibold text-2xl">Allowed Categories</h2>
                    <div className="border-2 border-slate-300 rounded-b w-full bg-slate-100 mt-4 min-h-80">
                        <div id="allowed" className="flex gap-3 m-3 flex-wrap">
                            <Category/>
                            <Category/>
                        </div>
                    </div>
                </div>
                <div className="flex-col">
                    <h2 className="text-center font-semibold text-2xl">Prohibited Categories</h2>
                    <div className="border-2 border-slate-300 rounded-b w-full bg-slate-100 mt-4 min-h-80">
                        <div id="prohibited" className="flex flex-wrap gap-3 m-3">
                            <Category/>
                            <Category/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Categories