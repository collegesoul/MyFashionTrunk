function Category () {
    const closeIcon = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                           stroke="currentColor" className="size-5">
        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"/>
    </svg>

    let tag = "Clothes";

    return(
        <div className="flex space-between py-1 px-2 gap-x-3 rounded-2xl items-center bg-amber-200 w-fit">
            <p className="ml-2">{tag}</p>
            <span className="cursor-pointer text-red-500">{closeIcon}</span>
        </div>
    );
}

export default Category