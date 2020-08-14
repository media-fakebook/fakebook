let upperNavProfile;
let myPagePeed;
let userName;

let myPage = {
    init: function () {
        let _this = this;
        _this.initVueInstance();
        _this.initVariables();

    },
    initVueInstance: function () {
        upperNavProfile = new Vue({
            el: ".user-profile",
            data: {
                userName: ''
            }
        });
        //[{"id":1,"content":"테스트 포스트입니다."},{"id":2,"content":"이것도 테스트입니다. "}]

        Vue.component('posts', {
            methods: {
                deletePost: function (postId) {
                    axios.delete("/post/delete/" + postId)
                        .then(function (response) {
                            console.log("delete post . id= " + response);
                            alert("게시물을 삭제했습니다. ");
                            window.location.reload();
                        }).catch(function (error) {
                        console.log(error);
                    })
                }
            },
            props: ['post', 'username'],
            template: '<div class="card widget-feed padding-15">\n' +
                '                    <div class="feed-header">\n' +
                '                        <ul class="list-unstyled list-info">\n' +
                '                            <li>\n' +
                '                                <img class="thumb-img img-circle" src="/images/default/profile-default.png" alt="">\n' +
                '                                <div class="info">\n' +
                '                                    <a href="" class="title no-pdd-vertical text-semibold inline-block">{{username}}</a>\n' +
                '                                    <span></span>\n' +
                '                                    <span class="sub-title">15시간</span>\n' +
                '                                    <a class="pointer absolute top-0 right-0" data-toggle="dropdown"\n' +
                '                                       aria-expanded="false">\n' +
                '                                        <span class="btn-icon text-dark">\n' +
                '                                            <i class="ti-more font-size-16"></i>\n' +
                '                                        </span>\n' +
                '                                    </a>\n' +
                '                                    <ul class="dropdown-menu">\n' +
                '                                        <li>\n' +
                '                                            <a class="pointer" v-on:click="deletePost(post.id)">\n' +
                '                                                <i class="ti-trash pdd-right-10 text-dark"></i>\n' +
                '                                                <span class="">게시글 삭제</span>\n' +
                '                                            </a>\n' +
                '                                        </li>\n' +
                '                                    </ul>\n' +
                '                                </div>\n' +
                '                            </li>\n' +
                '                        </ul>\n' +
                '                    </div>\n' +
                '                    <div class="feed-body no-pdd">\n' +
                '                        <p>\n' +
                '                            <span> {{post.content}} </span> <br>\n' +
                '                        </p>\n' +
                '                    </div>\n' +
                '                    <ul class="feed-action pdd-btm-5 border bottom">\n' +
                '                        <li>\n' +
                '                            <i class="fa fa-thumbs-o-up text-info font-size-16 mrg-left-5"></i>\n' +
                '                            <span class="font-size-14 lh-2-1">67</span>\n' +
                '                        </li>\n' +
                '                        <li class="float-right">\n' +
                '                            <span class="font-size-13">공유 78회</span>\n' +
                '                        </li>\n' +
                '                        <li class="float-right mrg-right-15">\n' +
                '                            <span class="font-size-13">댓글 2개</span>\n' +
                '                        </li>\n' +
                '                    </ul>\n' +
                '                    <ul class="feed-action border bottom d-flex">\n' +
                '                        <li class="text-center flex-grow-1">\n' +
                '                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">\n' +
                '                                <i class="fa fa-thumbs-o-up font-size-16"></i>\n' +
                '                                <span class="font-size-13">좋아요</span>\n' +
                '                            </button>\n' +
                '                        </li>\n' +
                '                        <li class="text-center flex-grow-1">\n' +
                '                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">\n' +
                '                                <i class="fa fa-comment-o font-size-16"></i>\n' +
                '                                <span class="font-size-13">댓글</span>\n' +
                '                            </button>\n' +
                '                        </li>\n' +
                '                        <li class="text-center flex-grow-1">\n' +
                '                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">\n' +
                '                                <i class="fa fa-share font-size-16"></i>\n' +
                '                                <span class="font-size-13">공유하기</span>\n' +
                '                            </button>\n' +
                '                        </li>\n' +
                '                    </ul>\n' +
                '                    <div class="feed-footer">\n' +
                '                        <div class="comment">\n' +
                '                            <ul class="list-unstyled list-info">\n' +
                '                                <li class="comment-item">\n' +
                '                                    <img class="thumb-img img-circle" src="/images/default/eastjun_profile.jpg" alt="">\n' +
                '                                    <div class="info">\n' +
                '                                        <div class="bg-lightgray border-radius-18 padding-10 max-width-100">\n' +
                '                                            <a href="" class="title text-bold inline-block text-link-color">eastjun</a>\n' +
                '                                            <span>크 멋져요. MVC패턴을 직접 프로젝트에 적용해봤나요?</span>\n' +
                '                                        </div>\n' +
                '                                        <div class="font-size-12 pdd-left-10 pdd-top-5">\n' +
                '                                            <span class="pointer text-link-color">좋아요</span>\n' +
                '                                            <span>·</span>\n' +
                '                                            <span class="pointer text-link-color">답글 달기</span>\n' +
                '                                            <span>·</span>\n' +
                '                                            <span class="pointer">2시간</span>\n' +
                '                                        </div>\n' +
                '                                    </div>\n' +
                '                                </li>\n' +
                '                            </ul>\n' +
                '                            <div class="add-comment">\n' +
                '                                <textarea rows="1" class="form-control" placeholder="댓글을 입력하세요.."></textarea>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </div>'
        })
        myPagePeed = new Vue({
            el: "#mypage-peed",
            data: {
                userName: '',
                posts: ''
            }
        })
    },
    initVariables: function () {
        //upperNavPofile, myPageProfile 초기화
        axios.get("/session/user")
            .then(function (response) {
                console.log(response);
                userName = response.data['name'];
                upperNavProfile.userName = userName;
                myPagePeed.userName = userName;
            }).catch(function (error) {
            console.log(error);
        });

        axios.get("/mypage/posts")
            .then(function (response) {
                myPagePeed.posts = response.data;
            }).catch(function (error) {
            console.log(error);
        });
    }
}
myPage.init();