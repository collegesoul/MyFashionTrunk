// eslint-disable-next-line no-unused-vars
import React, {useRef} from 'react';
import {NavLink} from "react-router";
import UserDropDown from "./UserDropDown.jsx";

function Navigation() {
    const arrow = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                       stroke="currentColor" className="size-3">
        <path strokeLinecap="round" strokeLinejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5"/>
    </svg>

    const isVisibleRef = useRef(null);

    const toggleUserDropDown = () => {
        if (isVisibleRef.current) {
            const isHidden = isVisibleRef.current.style.display === 'none';
            isVisibleRef.current.style.display = isHidden ? 'block' : 'none';
        }
    }

    return (
        <>
            <div className="fixed top-0 inset-x-0 w-full z-50">
                <nav className="navbar">
                    <div className="flex">
                        <NavLink to={"/"}>
                            <h1>Logo</h1>
                        </NavLink>
                    </div>
                    <ul className="flex lg:gap-x-20 gap-x-12">
                        <li>
                            <NavLink to="/"
                                     className={({isActive}) => isActive ? "text-gray-800 font-semibold" : "nav-link"}>
                                My Listings
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/categories"
                                     className={({isActive}) => isActive ? "text-gray-800 font-semibold" : "nav-link"}>
                                Categories
                            </NavLink>
                        </li>
                    </ul>
                    <div className="flex gap-x-2 items-center cursor-pointer relative" onClick={toggleUserDropDown}>
                        <span className="bg-gray-600 rounded-full w-6 h-6"></span>
                        <span className="text-gray-700 lg:inline hidden">Guest</span>
                        <span>{arrow}</span>
                    </div>
                </nav>
            </div>
            <div ref={isVisibleRef} style={{display: "none"}}>
                <UserDropDown/>
            </div>
        </>
    );

}

export default Navigation