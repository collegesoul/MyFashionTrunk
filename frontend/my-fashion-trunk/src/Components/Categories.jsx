import Button from "./Elements/Button.jsx";
import Category from "./Elements/Category.jsx";

function Categories() {
    return(
        <div>
            <h1 className="heading">Categories</h1>
            <div className="flex gap-x-10 font-semibold text-lg my-3">
                <span className="cursor-pointer text-gray-700 hover:text-gray-500">
                    <h3>Allowed</h3>
                </span>
                <span className="cursor-pointer text-gray-700 hover:text-gray-500 ">
                    <h3>Prohibited</h3>
                </span>
            </div>
            <hr className="mb-4 lg:w-8/12"/>
            <div className="flex">
                <input className="p-2 border-2 rounded-l w-7/12"
                       type="text" placeholder="Add New Category"/>
                <Button text="Add"/>
            </div>
            <div className="border-2 border-slate-300 rounded-b w-full lg:w-8/12 bg-slate-100 mt-4 min-h-80">
                <div className="flex gap-x-3 m-2">
                    <Category/>
                    <Category/>
                </div>
            </div>
        </div>
    );
}

export default Categories