import {NavLink} from "react-router";

function Navigation() {
    const arrow = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                       stroke="currentColor" className="size-3">
        <path strokeLinecap="round" strokeLinejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5"/>
    </svg>


    return (
        <div className="fixed top-0 inset-x-0 w-full z-50">
            <nav className="navbar">
                <div className="flex">
                    <NavLink to={"/"}>
                        <h1>Logo</h1>
                    </NavLink>
                </div>
                <ul className="flex lg:gap-x-20 gap-x-12">
                    <li>
                        <NavLink to="/">
                            My Listings
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/categories">
                            Categories
                        </NavLink>
                    </li>
                </ul>
                <div className="flex gap-x-2 items-center cursor-pointer">
                    <span className="bg-gray-600 rounded-full w-6 h-6"></span>
                    <span>Guest</span>
                    <span>{arrow}</span>
                </div>
            </nav>
        </div>
    );

}

export default Navigation