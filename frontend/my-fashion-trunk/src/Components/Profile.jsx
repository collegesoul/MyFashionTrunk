import Button from "./Elements/Button.jsx";

function Profile() {
    return(
        <div>
            <h1 className="heading">Profile Settings</h1>
            <div className="mt-7">
                <form>
                    <div>
                        <label className="form-label">Full Name:</label>
                        <input className="form-input w-7/12 lg:w-4/12"
                            name="name" type="text" value="Guest"/>
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Email:</label>
                        <input className="form-input w-7/12 lg:w-4/12"
                            name="name" type="email" value="guest@gmail.com"/>
                    </div>
                    <div className="mt-4">
                        <label className="form-label">Change Password:</label>
                        <input className="form-input w-7/12 lg:w-4/12"
                            name="name" type="password"/>
                    </div>
                    <div className="mt-6">
                        <Button text="Update Profile"/>
                    </div>
                </form>
            </div>
            <div className="mt-10">
                <h3 className="text-xl text-red-600 font-semibold">Delete Account</h3>
                <hr className="w-7/12 mt-3" />
                <div className="mt-3">
                    <p>Once you delete account, there&#39;s no going back. Please be certain</p>
                    <div className="mt-4">
                        <Button text="Delete your account" color="bg-red-500"/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Profile