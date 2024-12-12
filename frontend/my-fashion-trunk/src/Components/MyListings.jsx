// eslint-disable-next-line no-unused-vars
import React, {useEffect, useRef, useState} from 'react';
import Button from "./Elements/Button.jsx";
import Card from "./Elements/Card.jsx";
import FilterDropDown from "./FilterDropDown.jsx";
import {Link} from "react-router";
import axios from "axios";
import Notification from "./Elements/Notification.jsx";

/**
 * A component for displaying and managing user listings
 * @returns {JSX.Element} a rendered MyListings component
 * **/
function MyListings() {
    const funnel = <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                        viewBox="0 0 24 24" strokeWidth={1.5}
                        stroke="currentColor" className="size-4">
        <path strokeLinecap="round" strokeLinejoin="round"
              d="M12 3c2.755 0 5.455.232 8.083.678.533.09.917.556.917 1.096v1.044a2.25 2.25 0 0 1-.659
              1.591l-5.432 5.432a2.25 2.25 0 0 0-.659 1.591v2.927a2.25 2.25 0 0 1-1.244 2.013L9.75 21v-6.568a2.25
              2.25 0 0 0-.659-1.591L3.659 7.409A2.25 2.25 0 0 1 3 5.818V4.774c0-.54.384-1.006.917-1.096A48.32 48.32 0 0 1 12 3Z"
        />
    </svg>
    const user = JSON.parse(localStorage.getItem("user"));
    const [selectedValue, setSelectedValue] = useState("");
    const filterRef = useRef(null);
    const buttonRef = useRef(null);
    const [isDropDownVisible, setIsDropDownVisible] = useState(false);
    const [listings, setListings] = useState(()=>{
        const savedListings = localStorage.getItem("listings");
        return savedListings ? JSON.parse(savedListings) : [];
    });
    const [storedMessage, setStoredMessage] = useState(null);

    /**
     * fetches user listings from the server when the component mounts
     * **/
    useEffect(() => {
        const fetchData = async () => {
            try {
                const url = `${import.meta.env.VITE_SERVER_APP_URL}/api/v1/listings/${user.id}`;
                const response = await axios.get(url);
                setListings(response.data);
                localStorage.setItem("listings", JSON.stringify(response.data));
            }catch (error) {
                console.error("Error fetching listings:", error);
            }
        }
        fetchData();
    }, [user?.id])

    /**
     * toggles the visibility of the FilterDropDown menu
     * **/
    const toggleFilter = () => {
        setIsDropDownVisible((prev)=>!prev);

    }

    /**
     * Adds an event listener to detect clicks outside the FilterDropDown menu
     * to hide the dropdown if visible and if clicked outside
     * **/
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (
                filterRef.current && !filterRef.current.contains(event.target) &&
                buttonRef.current && !buttonRef.current.contains(event.target)
            ) {
                setIsDropDownVisible(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    /**
     * filters the listings based on the selected option ("Accepted", "Rejected")
     * **/
    useEffect(()=>{
        const listingsFromCache = JSON.parse(localStorage.getItem("listings"));
        if(selectedValue === "accepted") {
            setListings(listingsFromCache.filter((item)=>item.status === "Accepted"));
        } else if(selectedValue === "rejected") {
            setListings(listingsFromCache.filter((item)=>item.status === "Rejected"));
        }else {
            setListings(listingsFromCache);
        }
    },[selectedValue])

    /**
     * handles the selection of filter value
     * @param {string} value the selected filter value
     * **/
    const onCheck = (value)=>{
        setSelectedValue(value);
    }

    /**
     * resets the filter value
     * **/
    const onReset = ()=>{
        setSelectedValue("");
    }

    /**
     * handles the deletion of a user listing
     * @param {number} index the index of the listing to delete
     * **/
    const handleOnDelete = async (index) => {
       try {
           const id = listings[index].id;
           const url = `${import.meta.env.VITE_SERVER_APP_URL}/api/v1/listings/${id}`;
           const response = await axios.delete(url);
           setListings(listings.filter((item)=> item.id !== id))
           localStorage.setItem("listings", JSON.stringify(listings));
           setStoredMessage(response.data)
       } catch (error) {
           console.log(error);
       }
    }

    const handleNotificationDelete = ()=> {
        setStoredMessage(null);
    }

    useEffect(()=>{
        const message = JSON.parse(localStorage.getItem("message"));
        if(message){
            setStoredMessage(message);
            localStorage.removeItem("message");
        }
    }, [])

    return (
        <>
            <div className="flex justify-between items-center">
                <h1 className="heading">My Listings</h1>
                <span className="flex gap-x-5 items-center">
                    <button ref={buttonRef} onClick={toggleFilter}
                          className="flex items-center gap-x-1 cursor-pointer border-2
                          border-gray-500 py-2 px-4 hover:bg-gray-200 hover:border-gray-200">
                        {funnel}
                        Filter
                    </button>
                    <Link to="/create-new-listing">
                        <Button
                            text="Upload"
                            style="hover:bg-cyan-500"
                        />
                    </Link>
                </span>
            </div>
            {isDropDownVisible && (
                <div ref={filterRef} className="relative">
                    <FilterDropDown selected={selectedValue} onCheck={onCheck} onReset={onReset}/>
                </div>
            )}
            <div className="mt-7">
                {(listings.length === 0) ? (
                    <p className="text-center mt-4 text-2xl text-gray-700 font-semibold">No Listings to display</p>
                ): (
                    <div
                        className="lg:grid lg:grid-cols-4 lg:gap-y-4 lg:gap-x-0 gap-3 flex flex-wrap justify-center text-center">
                        {listings.map((item, id) => (
                            <span key={id}>
                            <Card title={item.title}
                                  status={item.status}
                                  category={item.category}
                                  imageUrl={item.imageUrl}
                                  imgAlt={item.title}
                                  onDelete={() => {handleOnDelete(id)}}
                            />
                        </span>
                        ))}
                    </div>
                )}
            </div>
            {storedMessage !== null && (
                <Notification text={storedMessage?.message}
                              onDelete={handleNotificationDelete}
                              type={storedMessage?.type}/>
            )}
        </>
    );
}

export default MyListings