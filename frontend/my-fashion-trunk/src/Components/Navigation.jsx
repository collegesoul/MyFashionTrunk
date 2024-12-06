// eslint-disable-next-line no-unused-vars
import React, {useRef} from 'react';
import {NavLink} from "react-router";
import UserDropDown from "./UserDropDown.jsx";

function Navigation() {
    const arrow = (
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-3">
            <path strokeLinecap="round" strokeLinejoin="round" d="M19.5 8.25L12 15.75 4.5 8.25" />
        </svg>
    );

    const briefcaseIcon = (
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-5">
            <path
                fillRule="evenodd"
                d="M7.5 5.25a3 3 0 0 1 3-3h3a3 3 0 0 1 3 3v.205c.933.085 1.857.197 2.774.334 1.454.218 2.476 1.483 2.476 2.917v3.033c0 1.211-.734 2.352-1.936 2.752A24.726 24.726 0 0 1 12 15.75c-2.73 0-5.357-.442-7.814-1.259-1.202-.4-1.936-1.541-1.936-2.752V8.706c0-1.434 1.022-2.7 2.476-2.917A48.814 48.814 0 0 1 7.5 5.455V5.25Zm7.5 0v.09a49.488 49.488 0 0 0-6 0v-.09a1.5 1.5 0 0 1 1.5-1.5h3a1.5 1.5 0 0 1 1.5 1.5Zm-3 8.25a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z"
                clipRule="evenodd"
            />
            <path
                d="M3 18.4v-2.796a4.3 4.3 0 0 0 .713.31A26.226 26.226 0 0 0 12 17.25c2.892 0 5.68-.468 8.287-1.335.252-.084.49-.189.713-.311V18.4c0 1.452-1.047 2.728-2.523 2.923-2.12.282-4.282.427-6.477.427a49.19 49.19 0 0 1-6.477-.427C4.047 21.128 3 19.852 3 18.4Z"
            />
        </svg>
    );

    const isVisibleRef = useRef(null);

    const toggleUserDropDown = () => {
        if (isVisibleRef.current) {
            const isHidden = isVisibleRef.current.style.display === "none";
            isVisibleRef.current.style.display = isHidden ? "block" : "none";
        }
    };


    const user = JSON.parse(localStorage.getItem("user")) || null;

    return (
        <>
            <div className="fixed top-0 inset-x-0 w-full z-50">
                <nav className="navbar">
                    <NavLink to={"/"}>
                        <div className="flex items-center gap-x-2">
                            <span>{briefcaseIcon}</span>
                            <h1 className="font-semibold">Fashion Trunk</h1>
                        </div>
                    </NavLink>
                    <ul className="flex lg:gap-x-20 gap-x-12">
                        <li>
                            <NavLink
                                to="/"
                                className={({isActive}) => (isActive ? "text-gray-800 font-semibold" : "nav-link")}
                            >
                                My Listings
                            </NavLink>
                        </li>
                        <li>
                            <NavLink
                                to="/categories"
                                className={({isActive}) => (isActive ? "text-gray-800 font-semibold" : "nav-link")}
                            >
                                Categories
                            </NavLink>
                        </li>
                    </ul>
                    <div className="flex gap-x-2 items-center cursor-pointer relative" onClick={toggleUserDropDown}>
                        <span className="bg-gray-600 rounded-full w-6 h-6"></span>
                        <span className="text-gray-700 lg:inline hidden">{user?.name || "Guest"}</span>
                        <span>{arrow}</span>
                    </div>
                </nav>
            </div>
            <div ref={isVisibleRef} style={{display: "none"}}>
                <UserDropDown name={user?.name || "Guest"}/>
            </div>
        </>
    );
}

export default Navigation