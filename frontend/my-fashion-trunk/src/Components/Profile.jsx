import Button from "./Elements/Button.jsx";
import {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router";
import Notification from "./Elements/Notification.jsx";

/**
 * A component for managing profile settings
 * @returns {JSX.Element} a rendered profile component
 * **/
function Profile() {
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem("user"));
    const [validationErrors, setValidationErrors] = useState({});
    const [formData, setFormData] = useState({
        id: user.id,
        name: user.name,
        surname: user.surname,
        email: user.email,
        password: "",
    });
    const [storedMessage, setStoredMessage] = useState(null);

    /**
     * handles change events for form inputs
     * @param {React.ChangeEvent<HTMLInputElement>} e the change event
     * **/
    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value})
    }

    /**
     * handles form submission for updating user profile
     * @param {React.ChangeEvent<HTMLFormElement>} e the form submission event
     * **/
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(formData);
            const response = await axios.put("http://localhost:8080/api/v1/user", formData);
            localStorage.setItem('user', JSON.stringify(response.data));
            setStoredMessage({
                "message": "Profile Edited Successfully",
                "type": "success",
            })
        } catch (error) {
            if (error.response && (error.response.status === 401
                || error.response.status === 400 || error.response.status === 409)) {
                setValidationErrors(error.response.data);
            }
            if (error.response && error.response.status === 404) {
                setStoredMessage({
                    "message": error.response.data.error,
                    "type": "error",
                })
            }
            console.log(error);
        }

    }

    /**
     * handles deletion of user account
     * **/
    const handleDelete = async () => {
        if (!user || !user.id) {
            console.error("User not found!");
            return;
        }
        const confirmDelete = window.confirm(
            "Are you sure you want to delete your account? This action cannot be undone.");
        if (!confirmDelete) return;

        try {
            const url = `http://localhost:8080/api/v1/user/${user.id}`;
            await axios.delete(url);
            localStorage.removeItem("user");
            navigate("/login");
        } catch (error) {
            console.error("Error deleting account:", error);
        }
    };

    /**
     * Sets storedMessage to null to stop the Notification component from rendering
     * **/
    const handleNotificationDelete = ()=> {
        setStoredMessage(null);
    }

    return(
        <div>
            <h1 className="heading">Profile Settings</h1>
            <div className="mt-7">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label className="form-label">Name:</label>
                        <input
                            className={validationErrors?.error || validationErrors?.name
                                ? "form-input w-7/12 lg:w-4/12 border-red-400 text-red-400 " +
                                "focus:outline-red-600 placeholder:text-red-500"
                                : "form-input w-7/12 lg:w-4/12 text-gray-600 focus:outline-gray-400 border-gray-300"}
                               name="name"
                               type="text"
                               value={formData.name}
                               onChange={handleChange}
                        />
                        {validationErrors && validationErrors?.field === "name" && (
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.error}</p>
                        ) || validationErrors && validationErrors?.name &&(
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.name}</p>
                        )}
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Surname:</label>
                        <input
                            className={validationErrors?.error || validationErrors?.surname
                                ? "form-input w-7/12 lg:w-4/12 border-red-400 text-red-400 " +
                                "focus:outline-red-600 placeholder:text-red-500"
                                : "form-input w-7/12 lg:w-4/12 text-gray-600 focus:outline-gray-400 border-gray-300"}
                               name="surname"
                               type="text"
                               value={formData.surname}
                               onChange={handleChange}
                        />
                        {validationErrors && validationErrors?.field === "surname" && (
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.error}</p>
                        ) || validationErrors && validationErrors?.name &&(
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.surname}</p>
                        )}
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Email:</label>
                        <input
                            className={validationErrors?.error || validationErrors?.email
                                ? "form-input w-7/12 lg:w-4/12 border-red-400 text-red-400 " +
                                "focus:outline-red-600 placeholder:text-red-500"
                                : "form-input w-7/12 lg:w-4/12 text-gray-600 focus:outline-gray-400 border-gray-300"}
                               name="email"
                               type="email"
                               value={formData.email}
                               onChange={handleChange}
                        />
                        {validationErrors && validationErrors?.field === "email" && (
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.error}</p>
                        ) || validationErrors && validationErrors?.email &&(
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.email}</p>
                        )}
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Change Password:</label>
                        <input
                            className={validationErrors?.error || validationErrors?.password
                                ? "form-input w-7/12 lg:w-4/12 border-red-400 text-red-400 " +
                                "focus:outline-red-600 placeholder:text-red-500"
                                : "form-input w-7/12 lg:w-4/12 text-gray-600 focus:outline-gray-400 border-gray-300"}
                               name="password"
                               type="password"
                               onChange={handleChange}
                        />
                        {validationErrors && validationErrors?.field === "password" && (
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.error}</p>
                        ) || validationErrors && validationErrors?.name &&(
                            <p className="text-red-600 text-sm font-semibold">{validationErrors.password}</p>
                        )}
                    </div>
                    <div className="mt-6">
                        <Button text="Update Profile"
                                type="submit"
                                style="hover:bg-cyan-500"
                        />
                    </div>
                </form>
            </div>
            <div className="mt-10">
                <h3 className="text-xl text-red-600 font-semibold">Delete Account</h3>
                <hr className="w-7/12 mt-3"/>
                <div className="mt-3">
                <p>Once you delete account, there&#39;s no going back. Please be certain</p>
                    <div className="mt-4">
                        <Button text="Delete your account"
                                color="bg-red-500"
                                clickEvent={handleDelete}
                        />
                    </div>
                </div>
            </div>
            {storedMessage !== null && (
                <Notification text={storedMessage?.message}
                              onDelete={handleNotificationDelete}
                              type={storedMessage?.type}/>
            )}
        </div>
    );
}

export default Profile