import Button from "./Elements/Button.jsx";
import Category from "./Elements/Category.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router";


function Categories() {
    useNavigate();
    const user = JSON.parse(localStorage.getItem("user"));
    const [categories, setCategories] = useState([]);
    const [categoryType, setCategoryType] = useState("");
    const [customCategory, setCustomCategory] = useState("");
    const [deletedCategoryId, setDeletedCategoryId] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/v1/categories");
                setCategories(response.data);
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        };
        fetchData();
    }, []);


    const prohibitedCategory = categories.filter((category) => category.type === "Prohibited");
    const allowedCategory = categories.filter((category) => category.type === "Allowed");


    const handleSelectedCategory = (e) => setCategoryType(e.target.value);
    const handleCustomCategory = (e) => setCustomCategory(e.target.value);

    // Handle adding a new category
    const handleAddCategory = async (e) => {
        e.preventDefault();
        const formData = {
            userId: user.id,
            name: customCategory,
            type: categoryType,
        };
        try {
            const response = await axios.post("http://localhost:8080/api/v1/categories", formData);
            setCategories((prevCategories) => [...prevCategories, response.data]);
            setCustomCategory("");
            setCategoryType("");
        } catch (error) {
            console.error("Error adding category:", error);
        }
    };


    const deleteProhibitedCategory = (index) => {
        const categoryToDelete = prohibitedCategory[index];
        setDeletedCategoryId(categoryToDelete.id);
    };


    const deleteAllowedCategory = (index) => {
        const categoryToDelete = allowedCategory[index];
        setDeletedCategoryId(categoryToDelete.id);
    };


    useEffect(() => {
        if (deletedCategoryId !== null) {
            const deleteCategory = async () => {
                try {
                    const url = `http://localhost:8080/api/v1/categories/${deletedCategoryId}`;
                    await axios.delete(url);
                    setCategories((prevCategories) =>
                        prevCategories.filter((category) => category.id !== deletedCategoryId)
                    );
                } catch (error) {
                    console.error("Error deleting category:", error);
                } finally {
                    setDeletedCategoryId(null); // Reset deleted category ID
                }
            };
            deleteCategory();
        }
    }, [deletedCategoryId]);

    return (
        <div>
            <h1 className="heading">Categories</h1>
            <hr className="my-4 lg:w-8/12" />
            <form onSubmit={handleAddCategory} className="flex">
                <input
                    className="p-2 border-2 rounded-l w-7/12"
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
                                <Category
                                    key={category.id}
                                    name={category.name}
                                    clickEvent={() => deleteAllowedCategory(index)}
                                />
                            ))}
                        </div>
                    </div>
                </div>
                <div className="flex-col">
                    <h2 className="text-center font-semibold text-2xl">Prohibited Categories</h2>
                    <div className="border-2 border-slate-300 rounded-b w-full bg-slate-100 mt-4 min-h-80">
                        <div className="flex flex-wrap gap-3 m-3">
                            {prohibitedCategory.map((category, index) => (
                                <Category
                                    key={category.id}
                                    name={category.name}
                                    clickEvent={() => deleteProhibitedCategory(index)}
                                />
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Categories;
