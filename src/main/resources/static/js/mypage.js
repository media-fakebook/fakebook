let myPage = {
    init: function () {
        let _this = this;
        _this.initMyPageProfile();
    },

    initMyPageProfile: function () {
        let userName = '';
        const upperNavProfile = new Vue({
            el: ".user-profile",
            data: {
                userName: userName
            }
        })
        const myPageProfile = new Vue({
            el: "#mypage-profile",
            data: {
                userName: userName
            }
        })
        axios.get("/session/user")
            .then(function (response) {
                console.log(response);
                userName = response.data['name'];
                upperNavProfile.userName = userName;
                myPageProfile.userName = userName;
            }).catch(function (error) {
            console.log(error);
        })
    },
    initMyPagePosts: function () {
        let posts = null;
        const myPagePosts = new Vue({})

    }
}
myPage.init();