import PropTypes from "prop-types";

function Category ({clickEvent, name ="No Category"}) {
    const closeIcon = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                           stroke="currentColor" className="size-5">
        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"/>
    </svg>


    return(
        <div className="flex space-between py-1 px-2 gap-x-3 rounded-2xl items-center bg-amber-200 w-fit">
            <p className="ml-2">{name}</p>
            <span onClick={clickEvent} className="cursor-pointer text-red-500">{closeIcon}</span>
        </div>
    );
}
Category.propTypes = {
    name: PropTypes.string,
    clickEvent: PropTypes.func,
}

export default Category