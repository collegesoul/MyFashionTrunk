import Button from "./Elements/Button.jsx";
import {NavLink, useNavigate} from "react-router";
import {useState} from "react";
import axios from "axios";

/**
 * A component for uploading new listings
 * @returns {JSX.Element} the rendered upload component
 * **/
function Upload() {
    const arrowBack = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                           stroke="currentColor" className="size-6">
        <path strokeLinecap="round" strokeLinejoin="round" d="M10.5 19.5 3 12m0 0 7.5-7.5M3 12h18"/>
    </svg>
    const navigate = useNavigate()
    const user = JSON.parse(localStorage.getItem("user"));
    const [title, setTitle] = useState("");
    const [image, setImage] = useState(null);
    const [validationErrors, setValidationErrors] = useState({});

    /**
     * handles change event for title input
     * @param{React.ChangeEvent<HTMLInputElement>} e the change event
     * **/
    const handleTitleChange = (e) => {
        setTitle(e.target.value);
    }

    /**
     * handles change event for file input
     * @param{React.ChangeEvent<HTMLInputElement>} e the change event
     * **/
    const handleFileChange = (e) => {
        setImage(e.target.files[0]);
    }

    /**
     * handles form submission for creating a listing
     * @param{React.ChangeEvent<HTMLFormElement>} e the form submission event
     * **/
    const handleSubmitForm = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("title", title);
        formData.append("image", image);
        formData.append("userId", user.id);
        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }

        // Querying api to create listing
        try {
            const response = await axios.post(
                `${import.meta.env.VITE_SERVER_APP_URL}/api/v1/listings`,
                formData,
                {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                }
            );
            localStorage.setItem('message', JSON.stringify(response.data))
            navigate("/");
        } catch (error) {
            if (error.response && (error.response.status === 404 || error.response.status === 400)) {
                if(error.response.data.message) {
                    if (title === "") {
                        setValidationErrors({
                            "image": "Image file is required",
                            "field": "file",
                            "title": "Title is required",
                        });
                    } else {
                        setValidationErrors({
                            "image": "Image file is required",
                            "field": "file"
                        });
                    }

                } else {
                    setValidationErrors(error.response.data);
                }
            }
            console.error(error);
        }
    }


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
                <form onSubmit={handleSubmitForm}>
                    <div>
                        <label className="form-label">Title:</label>
                        <input
                            className={validationErrors?.error || validationErrors?.title
                                ? "form-input w-10/12 lg:w-7/12 border-red-400 text-red-400 focus:outline-red-600 " +
                                "placeholder:text-red-500"
                                : "form-input w-10/12 lg:w-7/12 text-gray-600 focus:outline-gray-400 border-gray-300"}
                               name="name" type="text"
                               placeholder="Name of Listing"
                               value={title}
                               onChange={handleTitleChange}
                        />
                        {validationErrors && (validationErrors?.field === "title" || validationErrors?.title ) && (
                            <p className="text-red-600 text-sm font-semibold">
                                {validationErrors.error || validationErrors.title }
                            </p>
                        )}
                    </div>
                    <div className="mt-8">
                        <label className="form-label">Upload image:</label>
                        <input className="w-10/12 lg:w-7/12 block border-2 rounded text-gray-600 cursor-pointer
                        italic file:bg-gray-500 file:border-0 file:rounded file:p-1.5
                        file:text-md file:m-1 file:cursor-pointer file:font-medium file:text-white file:not-italic"
                               name="file" type="file"
                               onChange={handleFileChange}
                        />
                        {validationErrors && validationErrors?.field === "file" && (
                            <p className="text-red-600 text-sm font-semibold">
                                {validationErrors.image || validationErrors.error }
                            </p>
                        )}
                    </div>
                    <div className="mt-10">
                        <Button
                            text="Create New listing"
                            type="submit"
                            style="hover:bg-cyan-500"
                        />
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Upload