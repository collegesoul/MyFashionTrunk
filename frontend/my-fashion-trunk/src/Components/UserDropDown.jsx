import Button from "./Elements/Button.jsx";
import {NavLink, useNavigate} from "react-router";
import PropTypes from "prop-types";
/**
 * A dropdown component for viewing user edit profile and logout button
 * @param {string} name the name of the user
 * @returns {JSX.Element} the rendered user dropdown component
 * **/
function UserDropDown({name}){
    const navigate = useNavigate();

    /**
     * handles logout action by removing the user from local storage and navigating
     * to the login route
     * **/
    const handleSignOut = () => {
        localStorage.removeItem("user");
        navigate("/login");
    }

    return (
        <div className="modal right-3 top-12 px-5 pb-7 pt-4 lg:w-52 w-50">
            <div className="grid grid-cols-1 place-items-center gap-y-5">
                <div className="flex items-center gap-x-2.5">
                    <span className="bg-gray-600 rounded-full w-6 h-6 inline-block"></span>
                    <span className="text-gray-700 text-lg">{name !== null && name !== "" ? name : "Guest"}</span>
                </div>
                <div className="cursor-pointer lg:text-start hover:bg-gray-200 p-2 w-full">
                    <NavLink to="/profile">
                        <p className="text-center">Edit Profile</p>
                    </NavLink>
                </div>
                <div>
                    <Button text="Logout" color="bg-red-400" style="hover:bg-red-500" clickEvent={handleSignOut}/>
                </div>
            </div>
        </div>
    );

}

UserDropDown.propTypes = {
    name: PropTypes.string,
}

export default UserDropDown