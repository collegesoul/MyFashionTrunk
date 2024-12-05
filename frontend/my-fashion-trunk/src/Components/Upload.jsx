// eslint-disable-next-line no-unused-vars
import React, {useRef} from "react";
import Button from "./Elements/Button.jsx";
import {NavLink} from "react-router";

function Upload() {
    const arrowBack = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                           stroke="currentColor" className="size-6">
        <path strokeLinecap="round" strokeLinejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18"/>
    </svg>


    return (
        <div>
            <h1 className="heading">Add New Listings</h1>
            <hr className="my-4 lg:w-7/12"/>
            <NavLink to="/">
                <div className={"cursor-pointer"}>
                    {arrowBack}
                </div>
            </NavLink>
            <div className="mt-7">
                <form>
                    <div>
                        <label className="form-label">Title:</label>
                        <input className="form-input w-10/12 lg:w-7/12 placeholder:italic"
                               name="name" type="text" placeholder="Name of Listing"/>
                    </div>
                    <div className="mt-8">
                        <label className="form-label">Upload image:</label>
                        <input className="w-10/12 lg:w-7/12 block border-2 rounded text-gray-600 cursor-pointer
                        placeholder:italic file:bg-gray-500 file:border-0 file:rounded file:p-1.5
                        file:text-md file:m-1 file:cursor-pointer file:font-medium file:text-white"
                               name="file" type="file"/>
                    </div>
                    <div className="mt-10">
                        <Button text="Create New listing"/>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Upload