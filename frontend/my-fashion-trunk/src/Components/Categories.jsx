import Button from "./Elements/Button.jsx";
import Category from "./Elements/Category.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router";
import Notification from "./Elements/Notification.jsx";

/**
 * A component for managing categories
 * @returns {JSX.Element} a rendered Categories component
 * **/
function Categories() {
    useNavigate();
    const user = JSON.parse(localStorage.getItem("user"));
    const [categories, setCategories] = useState([]);
    const [categoryType, setCategoryType] = useState("");
    const [customCategory, setCustomCategory] = useState("");
    const [deletedCategoryId, setDeletedCategoryId] = useState(null);
    const [errors, setErrors] = useState(null);

    /**
     * fetches categories from the server when the component mounts
     * **/
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${import.meta.env.VITE_SERVER_APP_URL}/api/v1/categories`);
                setCategories(response.data);
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        };
        fetchData();
    }, []);

    const prohibitedCategory = categories.filter((category) => category.type === "Prohibited");
    const allowedCategory = categories.filter((category) => category.type === "Allowed");

    /**
     * handles selection of category type
     * **/
    const handleSelectedCategory = (e) => setCategoryType(e.target.value);
    /**
     * handles input change for the custom category name
     * **/
    const handleCustomCategory = (e) => setCustomCategory(e.target.value);

    /**
     * handles form submission for adding a new category
     * @param {React.ChangeEvent<HTMLFormElement>} e the form submission event
     * **/
    const handleAddCategory = async (e) => {
        e.preventDefault();
        const formData = {
            userId: user.id,
            name: customCategory,
            type: categoryType,
        };
        try {
            const response = await axios.post(
                `${import.meta.env.VITE_SERVER_APP_URL}/api/v1/categories`, formData);
            setCategories((prevCategories) => [...prevCategories, response.data]);
            setCustomCategory("");
            setCategoryType("");
        } catch (error) {
            if (error.response && error.response.status === 400 || error.response.status === 409) {
                setErrors({
                    "message": error.response.data.error,
                    "type": "error",
                });
            }
            console.error(error);
        }
    };

    /**
     * sets the deletedCategoryId to the id of the category to be deleted in the prohibited category list
     * @param {number} index the index of the category to delete
     * **/
    const deleteProhibitedCategory = (index) => {
        const categoryToDelete = prohibitedCategory[index];
        setDeletedCategoryId(categoryToDelete.id);
    };

    /**
     * sets the deletedCategoryId to the id of the category to be deleted in the allowed category list
     * @param {number} index the index of the category to delete
     * **/
    const deleteAllowedCategory = (index) => {
        const categoryToDelete = allowedCategory[index];
        setDeletedCategoryId(categoryToDelete.id);
    };

    /**
     * deletes the category with the specified id stored in the deletedCategoryId
     * **/
    useEffect(() => {
        if (deletedCategoryId !== null) {
            const deleteCategory = async () => {
                try {
                    const url = `${import.meta.env.VITE_SERVER_APP_URL}/api/v1/categories/${deletedCategoryId}`;
                    await axios.delete(url);
                    setCategories((prevCategories) =>
                        prevCategories.filter((category) => category.id !== deletedCategoryId)
                    );
                } catch (error) {
                    console.error("Error deleting category:", error);
                } finally {
                    setDeletedCategoryId(null);
                }
            };
            deleteCategory();
        }
    }, [deletedCategoryId]);

    /**
     * Sets storedMessage to null to stop the Notification component from rendering
     * **/
    const handleNotificationDelete = ()=> {
        setErrors(null);
    }

    return (
        <div>
            <h1 className="heading">Categories</h1>
            <hr className="my-4 lg:w-8/12" />
            <form onSubmit={handleAddCategory} className="flex">
                <input
                    className="p-2 border-2 rounded-l w-7/12 placeholder:italic"
                    type="text"
                    placeholder="Add New Category"
                    value={customCategory}
                    onChange={handleCustomCategory}
                />
                <select
                    className="border-y-2 p-1"
                    value={categoryType}
                    onChange={handleSelectedCategory}
                >
                    <option value="">Select Type</option>
                    <option value="Allowed">Allowed</option>
                    <option value="Prohibited">Prohibited</option>
                </select>
                <Button text="Add" type="submit" />
            </form>
            <div className="grid grid-cols-2 gap-x-6 lg:gap-x-16 mt-6">
                <div className="flex-col">
                    <h2 className="text-center font-semibold text-2xl">Allowed Categories</h2>
                    <div className="border-2 border-slate-300 rounded-b w-full bg-slate-100 mt-4 min-h-80">
                        <div className="flex gap-3 m-3 flex-wrap">
                            {allowedCategory.map((category, index) => (
                                (category.user === null) ? (
                                    <Category
                                        key={category.id}
                                        name={category.name}
                                        canDelete={false}
                                    />
                                ) : (
                                    <Category
                                        key={category.id}
                                        name={category.name}
                                        clickEvent={() => deleteAllowedCategory(index)}
                                    />
                                )
                            ))}
                        </div>
                    </div>
                </div>
                <div className="flex-col">
                    <h2 className="text-center font-semibold text-2xl">Prohibited Categories</h2>
                    <div className="border-2 border-slate-300 rounded-b w-full bg-slate-100 mt-4 min-h-80">
                        <div className="flex flex-wrap gap-3 m-3">
                            {prohibitedCategory.map((category, index) => (
                                (category.user === null) ? (
                                    <Category
                                        key={category.id}
                                        name={category.name}
                                        canDelete={false}
                                    />
                                ) : (
                                    <Category
                                        key={category.id}
                                        name={category.name}
                                        clickEvent={() => deleteProhibitedCategory(index)}
                                    />
                                )
                            ))}
                        </div>
                    </div>
                </div>
            </div>
            {errors !== null && (
                <Notification text={errors?.message}
                              onDelete={handleNotificationDelete}
                              type={errors?.type}/>
            )}
        </div>
    );
}

export default Categories;
