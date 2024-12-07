// eslint-disable-next-line no-unused-vars
import React, {useEffect, useRef, useState} from 'react';
import Button from "./Elements/Button.jsx";
import Card from "./Elements/Card.jsx";
import FilterDropDown from "./FilterDropDown.jsx";
import {Link} from "react-router";
import axios from "axios";

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
    const filterRef = useRef(null);
    const [listings, setListings] = useState([]);
    const [listingId, setListingId] = useState(0);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const url = `http://localhost:8080/api/v1/listings/${user.id}`;
                const response = await axios.get(url);
                console.log(response.data);
                setListings(response.data);
            }catch (error) {
                console.error("Error fetching listings:", error);
            }
        }
        fetchData();
    }, [listings, user.id])


    const toggleFilter = () => {
        if (filterRef.current) {
            const isHidden = filterRef.current.style.display === 'none';
            filterRef.current.style.display = (isHidden) ? 'block' : 'none';
        }
    }
    
    useEffect(()=>{
        const fetchData = async () => {
            try {
                const url = `http://localhost:8080/api/v1/listings/${listingId}`;
                await axios.delete(url);
            } catch (error) {
                console.log(error);
            }
        }
        fetchData();
    }, [listingId])

    const handleOnDelete = (index) => {
        const id = listings[index].id;
        setListingId(id);
        listings.filter((_, i)=> i === index)
    }


    return (
        <>
            <div className="flex justify-between items-center">
                <h1 className="heading">My Listings</h1>
                <span className="flex gap-x-5 items-center">
                    <span onClick={toggleFilter}
                          className="flex items-center gap-x-1 cursor-pointer border-2
                          border-gray-500 py-2 px-4 hover:bg-gray-200 hover:border-gray-200">
                        {funnel}
                        Filter
                    </span>
                    <Link to="/create-new-listing">
                        <Button
                            text="Upload"
                            style="hover:bg-cyan-500"
                        />
                    </Link>
                </span>
            </div>
            <div ref={filterRef} className="relative" style={{display:'none'}}>
                <FilterDropDown/>
            </div>
            <div className="mt-7">
                {(listings.length === 0)? (
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
        </>
    );
}

export default MyListings