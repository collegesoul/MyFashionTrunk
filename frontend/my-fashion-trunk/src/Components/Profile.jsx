import Button from "./Elements/Button.jsx";
import {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router";

function Profile() {
    const user = JSON.parse(localStorage.getItem("user"));
    const [formData, setFormData] = useState({...user});
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value})
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(formData);
            const response = await axios.put("http://localhost:8080/api/v1/user", formData);
            localStorage.setItem('user', JSON.stringify(response.data));
        } catch (error) {
            console.log(error);
        }

    }
    //TODO: Look at this later
    const handleDelete = async () => {
        if (!user || !user.id) {
            console.error("User not found!");
            return;
        }
        const confirmDelete = window.confirm("Are you sure you want to delete your account? This action cannot be undone.");
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


    return(
        <div>
            <h1 className="heading">Profile Settings</h1>
            <div className="mt-7">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label className="form-label">Name:</label>
                        <input className="form-input w-7/12 lg:w-4/12"
                               name="name"
                               type="text"
                               value={formData.name}
                               onChange={handleChange}
                        />
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Surname:</label>
                        <input className="form-input w-7/12 lg:w-4/12"
                               name="surname"
                               type="text"
                               value={formData.surname}
                               onChange={handleChange}
                        />
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Email:</label>
                        <input className="form-input w-7/12 lg:w-4/12"
                               name="email"
                               type="email"
                               value={formData.email}
                               onChange={handleChange}
                        />
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Change Password:</label>
                        <input className="form-input w-7/12 lg:w-4/12"
                               name="password"
                               type="password"
                               onChange={handleChange}
                        />
                    </div>
                    <div className="mt-6">
                        <Button text="Update Profile"
                                type="submit"
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
        </div>
    );
}

export default Profile