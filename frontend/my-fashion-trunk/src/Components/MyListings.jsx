// eslint-disable-next-line no-unused-vars
import React, {useRef} from 'react';
import Button from "./Elements/Button.jsx";
import Card from "./Elements/Card.jsx";
import FilterDropDown from "./FilterDropDown.jsx";
import Upload from "./Upload.jsx";

function MyListings() {
    const funnel = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                        stroke="currentColor" className="size-4">
        <path strokeLinecap="round" strokeLinejoin="round"
              d="M12 3c2.755 0 5.455.232 8.083.678.533.09.917.556.917 1.096v1.044a2.25 2.25 0 0 1-.659 1.591l-5.432 5.432a2.25 2.25 0 0 0-.659 1.591v2.927a2.25 2.25 0 0 1-1.244 2.013L9.75 21v-6.568a2.25 2.25 0 0 0-.659-1.591L3.659 7.409A2.25 2.25 0 0 1 3 5.818V4.774c0-.54.384-1.006.917-1.096A48.32 48.32 0 0 1 12 3Z"/>
    </svg>
    const filterRef = useRef(null);
    const cardItems = [
        {listingTitle: 'Baby Powder', listingStatus: 'Rejected'},
        {listingTitle: '', listingStatus: 'Accepted'},
        {listingTitle: 'Orange', listingStatus: "Rejected"},
        {listingTitle: '', listingStatus: 'Pending'},
        {listingTitle: '', listingStatus: 'Accepted'},
        {listingTitle: 'New hats', listingStatus: 'Pending'},
        {listingTitle: '', listingStatus: 'Accepted'},
        {listingTitle: 'Necklace', listingStatus: 'Accepted'},
    ];


    const toggleFilter = () => {
        if (filterRef.current) {
            const isHidden = filterRef.current.style.display === 'none';
            filterRef.current.style.display = (isHidden) ? 'block' : 'none';
        }
    }


    return (
        <>
            <div className="flex justify-between items-center">
                <h1 className="heading">My Listings</h1>
                <span className="flex gap-x-5 items-center">
                    <span onClick={toggleFilter}
                          className="flex items-center gap-x-1 cursor-pointer border-2 border-gray-500 py-2 px-4 hover:bg-gray-200 hover:border-gray-200">
                        {funnel}
                        Filter
                    </span>
                    <Button text="Upload" style="hover:bg-cyan-500"/>
                </span>
            </div>
            <div ref={filterRef} className="relative" style={{display:'none'}}>
                <FilterDropDown/>
            </div>
            {/*<div>*/}
            {/*    <Upload/>*/}
            {/*</div>*/}
            <div className="mt-7">
                <div className="lg:grid lg:grid-cols-4 lg:gap-y-4 lg:gap-x-0 gap-3 flex flex-wrap justify-center text-center">
                    {cardItems.map((item, id) => (
                        <span key={id}>
                            <Card title={item.listingTitle} status={item.listingStatus}/>
                        </span>
                    ))}
                </div>
            </div>
        </>
    );
}

export default MyListings