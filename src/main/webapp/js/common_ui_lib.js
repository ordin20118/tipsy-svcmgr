
	
	const TYPE_PROD    = 100;
	const TYPE_REVIEW  = 210;
	const TYPE_COMPARE = 300;
	
	const TYPE_REVIEW_EXT         = 220;
	const TYPE_REVIEW_EXT_VIDEO   = 221;
	const TYPE_REVIEW_EXT_ARTICLE  = 222;
	
	const VIEW_CARD_TYPE = 0;
	const VIEW_FLAT_TYPE = 1;
	
	const SORT_RATING = "rating";
	const SORT_POPULARITY = "popularity";
	const SORT_LATEST = "latest";
	
	const SORT_TYPE_DESC = 'desc';
	const SORT_TYPE_ASC = 'asc';
	
	const STATE_SUCCESS = 0;
	const STATE_ERROR = 1;
	const STATE_DUPLICATION = 10;
	
	const LOGIN_TYPE_TEMP = 5;
	
	// 제품 비교
	function makeCompareItem(prodInfo) {
		
		console.log("[makeProdInfoHtmlLiType03Left]");
		console.log(prodInfo);
		
		let prod = prodInfo['prod'];
		let imageList = prodInfo['image_list'];

		let imgSrc = prefix+"/image/img_general_product_default.png";
		if(imageList != null && imageList.length > 0){
			imgSrc = imgPrefix +'/'+ imageList[0]['image_path']+"_300.png";
		}
		
		
		let appendLi = '<li id="compare_li_' + prod['prod_id'] + '">' +
					   '	<div class="list_ea_card">' +
					   '		<div class="btn_like"><input type="checkbox" class="l_checkbox" id="like0_'+prod['prod_id']+'"><label for="like0_'+prod['prod_id']+'"><em></em></label></div>'+
					   '		<div class="btn_select"><input type="checkbox" class="s_checkbox" name="compare_item"  id="compare_check_'+prod['prod_id']+'"><label for="compare_check_'+prod['prod_id']+'"><em></em></label></div>' +
					   '		<div class="thumb_wrap">' +
					   '			<div class="thumb">' +
					   '				<img src="'+imgSrc+'" class="img_type_review">'+
					   '			</div>' +
					   '		</div>' +
					   '		<div class="title">' +
					   '			<p class="ellipsis_2"><a href=""><span class="icon_p">P</span>'+prod['prod_name']+'</a></p>'+
					   '		</div>' +
					   '		<div class="evalue"><em class="star"></em>'+prodInfo['avg_rate'].toFixed(1)+'<i>('+prodInfo['rating_count']+')</i></div>'+
					   '		<div class="data"><strong>리뷰</strong>'+prodInfo['review_count']+'<span>|</span><strong>담기</strong>'+prodInfo['pick_count']+'</div>'+
					   '		<div class="cate">'+prodInfo['categ_disp']+'</div>'+
					   '	</div>' +
					   '</li>';
	
		return appendLi;
	}
	
	
	function avgRateToImageId(avgRate) {
		return avgRateToImg('rate_star', avgRate);
	}
	
	function avgRateToImg(prefix, avgRate) {
		let imageId = prefix +'_00';
		if(avgRate < 0.5) {
			imageId = prefix +'_00';
			
		} else if(avgRate < 1.0) {
			imageId = prefix +'_05';
			
		} else if(avgRate < 1.5) {
			imageId = prefix +'_10';
			
		} else if(avgRate < 2.0) {
			imageId = prefix +'_15';
			
		} else if(avgRate < 2.5) {
			imageId = prefix +'_20';
			
		} else if(avgRate < 3.0) {
			imageId = prefix +'_25';
			
		} else if(avgRate < 3.5) {
			imageId = prefix +'_30';
			
		} else if(avgRate < 4.0) {
			imageId = prefix +'_35';
			
		} else if(avgRate < 4.5) {
			imageId = prefix +'_40';
			
		} else if(avgRate < 5.0) {
			imageId = prefix +'_45';
		} else {
			imageId = prefix +'_50';
		}
		return imageId;
	}
	
	
	function delComment(comId){
		var answer = confirm("정말 삭제하시겠습니까?")
		if(answer){
			var reqParam = {
					commentId : comId
			}
			
			var reqUrl = prefix+"/api/prod/deleteComm.do"
			
			$.ajax({
				url:reqUrl,
				type : "post",
				data : reqParam,
				success : function(data){
					if(data.state==0){
						$("#li_comment_"+comId).remove();
					}else if(data.state==40006){
						alert("삭제 권한이 없습니다.");
					}else{
						alert("로그인 되지 않은 사용자입니다.");
					}
				},
				error : function(request,status,error){
					alertNetworkErr();
				}
			})
		}
		
	}
	
	function logout(func) {
		
		var url = prefix + "/api/login/logout.do";
		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	
	        	func(data, null);
	        	
	        },
	        error:function(request, status, error){
	        	func(null, error);
	        }
	    });	
		
	}

	function getSessionInfo(func, showAll) {
		
		var url = prefix + "/api/member_info/session_userinfo.do";
		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	func(data, null);        	
	        },
	        error:function(request, status, error){
	        	func(null, error);
	        }
	    });
		
	}

	function shareKakaotalk(kkShareData) {
	        
	    Kakao.Link.sendDefault({
	          objectType:"feed",
	          content : {
	              'title':       kkShareData['title'],
	              'description': kkShareData['desc'],
	              'imageUrl':    kkShareData['image'], 
	              'link' : {
	                  'mobileWebUrl': kkShareData['link'],
	                  'webUrl':       kkShareData['link']
	            }
	        }
	    });
	}
	
	function moveProd(prodId) {
		location.href = prefix + '/m/prod/product.do?prod_id=' + prodId;
	}
	
	function moveReview(reviewId) {
		location.href = prefix + '/m/review/review.do?review_id=' + reviewId;
	}
	
	function moveChannelByReviewId(reviewId){
		location.href = prefix + '/m/channel/channel.do?type=0&id=' + reviewId;
	}
	
	function moveChannelByChannelId(channelId){
		location.href = prefix + '/m/channel/channel.do?type=1&id=' + channelId;
	}
	
	function moveExtReview(relId) {
		log("[moveToExt]:"+prefix + "/m/review/ext.do?rel_id="+relId);
		window.open(prefix + "/m/review/ext.do?rel_id="+relId);
	}
	
	function moveCompare(compareId){
		location.href = prefix + "/m/prod/cate_compare_by_compareid.do?compareId="+compareId
	}
	
	function makeReviewLi(reviewInfo, type, isCateg){
				
		//let review = reviewInfo['review'];
		let review = reviewInfo['review'];
		let imageList = reviewInfo['image_list'];
		let likeStat = reviewInfo['like_stat']
		let imgSrc = prefix+"/image/img_general_product_default.png";;
			
		
		if(imageList != null && imageList.length > 0){
			imgSrc = imgPrefix +'/'+ imageList[0]['image_path']+"_300.png";
		}		
		
		let regDate = "-";
		if(review['reg_date'] != null){
			regDate = moment(review['reg_date']).format('YYYY.MM.DD');
		}
		
		likeCount = 0;
		if(likeStat != null){
			likeCount = likeStat['like_count'];
		}
		
		let myPickedChed = reviewInfo['my_pick_count'] == 0 ? '' : 'checked';
		let cInfoType    = reviewInfo['content_info_type'];
		let cbPrefix = 'rint';
		let cbId = randomString();
		
		
		if(type == VIEW_CARD_TYPE){
			let li = '<li>'+
				 '	<div class="list_ea_card">'+
				 '		<div class="btn_like"><input onclick="pickContentGlobal('+cInfoType+', '+review['review_id']+', this)" type="checkbox" class="l_checkbox" name="like_cb" data="like_'+cbPrefix+'_'+review['review_id']+'" id="'+cbId+'" '+myPickedChed+' ><label for="'+cbId+'"><em></em></label></div>'+
				 '		<div class="thumb_wrap" onclick="moveReview('+review['review_id']+')">'+
				 '			<div class="thumb">'+
				 '				<img src="'+imgSrc+'" class="img_type_review">'+
				 '			</div>'+
				 '		</div>'+
				 '		<div class="title">'+
				 '			<p class="ellipsis_2"><a href="javascript:moveReview('+review['review_id']+')"><span class="icon_r">R</span>'+review['title']+'</a></p>'+
				 '		</div>'+
				 '		<div class="agree"><em class="agr"></em>공감<i>('+likeCount+')</i></div>'+
				 '		<div class="data" onclick="moveChannelByReviewId('+review['review_id']+')">'+reviewInfo['author']+'<span>|</span>'+regDate+'</div>';				 
			if(!isCateg){
				li = li + '	<div class="cate">'+reviewInfo['categ_disp']+'</div>';	
			}		 
			li = li +'	</div>'+
				 '</li>';
			return li;
		}else if(type == VIEW_FLAT_TYPE){
			let li = '<li>'+
				 '	<div class="list_ea_flat">'+
				 '		<div class="title">'+
				 '			<p class="ellipsis_1"><a href="javascript:moveReview('+review['review_id']+')"><span class="icon_r">R</span>'+review['title']+'</a></p>'+
				 '		</div>'+
				 '		<div class="img_desc" onclick="moveReview('+review['review_id']+')">'+
				 '			<div class="img">'+
				 '				<img src="'+imgSrc+'" class="img_type_review">'+
				 '			</div>'+
				 '			<div class="desc ellipsis_3">'+review['preview']+
				 '			</div>'+
				 '		</div>'+
				 '		<div class="btn_like"><input onclick="pickContentGlobal('+cInfoType+', '+review['review_id']+', this)" type="checkbox" class="l_checkbox" name="like_cb" data="like_'+cbPrefix+'_'+review['review_id']+'" id="'+cbId+'" '+myPickedChed+' ><label for="'+cbId+'"><em></em></label></div>'+
				 '		<div class="agree"><em class="agr"></em>공감<i>('+likeCount+')</i></div>'+
				 '		<div class="data"><strong>조회</strong>'+reviewInfo['view_count']+'<span>|</span><span onclick="moveChannelByReviewId('+review['review_id']+')">'+reviewInfo['author']+'</span><span>|</span>'+regDate+'<span>|</span><strong >'+msgBtnPick+'</strong>'+reviewInfo['pick_count']+'</div>';
			if(!isCateg){
				li = li + '	<div class="cate">'+reviewInfo['categ_disp']+'</div>';	
			}		 
			li = li +'	</div>'+
				 '</li>';
			return li;
		}
	}
	
	
	
	
	function pickContentGlobal(type, contentId, cbObj){
		let checked  = $(cbObj).is(":checked");
		let inCbData = $(cbObj).attr("data");
		//log("pickContentGlobal inCbData["+inCbData+"] checked["+checked+"]")
		
		$("input[name=like_cb]:checkbox").each(function() {
			if(inCbData == $(this).attr("data") && $(this).attr("id") != "") {
				$(this).prop("checked", checked);
				//log("SET CHECK  " + $(this).attr("id"));
				//log($(this));
			}
		});
		
		
		let url = prefix + "/api/member_pick/pick_content.do?pick_type="+type+"&content_id="+contentId + "&pick_status=" + checked;
		//log("pickContentGlobal ["+url+"]");
		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	if(data['state'] == 0) {
	        		//Nothing!!
	        	} else {
	        		alert(getErrorMsg(data['state']));
	        		//$(cbObj).attr("checked", !checked);
	        		$("input[name=like_cb]:checkbox").each(function() {
						if(inCbData == $(this).attr("data")) {
							$(this).prop("checked", !checked);
						}
					});
	        	}
	        },
	        error:function(request, status, error){
	        	alertNetworkErr();
	        	$(cbObj).attr("checked", !checked);
	        	$("input[name=like_cb]:checkbox").each(function() {
					if(inCbData == $(this).attr("data")) {
						$(this).prop("checked", !checked);
					}
				});
	        }
	    });
	}
	
	function randomString() {
		let chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
		let string_length = 15;
		let randomstring = '';
		for (let i=0; i<string_length; i++) {
			let rnum = Math.floor(Math.random() * chars.length);
			randomstring += chars.substring(rnum,rnum+1);
		}
		//document.randform.randomfield.value = randomstring;
		return randomstring;
	}


	function makeProdLi(prodInfo, type, isCateg, idx){
						
		let prod = prodInfo['prod'];
		let imageList = prodInfo['image_list'];

		let imgSrc = prefix+"/image/img_general_product_default.png";
		if(imageList != null && imageList.length > 0){
			imgSrc = imgPrefix +'/'+ imageList[0]['image_path']+"_300.png";
		}
		
		let feature = "";
		if(prod['feature_json'] != null && prod['feature_json'].length != 0) {
			let featArr = JSON.parse(prod['feature_json']);
			for(let i=0; i<featArr.length; i++) {
				if(i == featArr.length-1){
					feature += "<span>"+featArr[i]+"</span>";
				}else{
					feature += "<span>"+featArr[i]+" : </span>";
				}
				
			}
		}
		
		let rateImg = avgRateToImageId(prodInfo['avg_rate'].toFixed(1));
		let myPickedChed = prodInfo['my_pick_count'] == 0 ? '' : 'checked';
		let cInfoType = prodInfo['content_info_type'];
		let cbPrefix = 'prod';
		let cbId = randomString();
		
		if(type == VIEW_CARD_TYPE){	
			//log("checked ["+checked+"]")
			let li = '<li>'+
					 //'	<div class="list_ea_card" onclick="moveProd('+prod['prod_id']+')">'+
					 '	<div class="list_ea_card">'+
					 '		<div class="btn_like"><input onclick="pickContentGlobal('+cInfoType+', '+prod['prod_id']+', this)" type="checkbox" class="l_checkbox" name="like_cb" data="like_'+cbPrefix+'_'+prod['prod_id']+'" id="'+cbId+'" '+myPickedChed+' ><label for="'+cbId+'"><em></em></label></div>';
			if(isCateg){
				li = li + ' <div class="btn_select"><input type="checkbox" class="s_checkbox" id="compare_'+prod['prod_id']+'" onclick="pushCompareTab(this,'+idx+');"><label for="compare_'+prod['prod_id']+'"><em></em></label></div>';
			}
			li = li +'		<div class="thumb_wrap" onclick="moveProd('+prod['prod_id']+')">'+
					 '			<div class="thumb">'+
					 '				<img src="'+imgSrc+'" class="img_type_product">'+
					 '			</div>'+
					 '		</div>'+
					 '		<div class="title">'+
					 '			<p class="ellipsis_2"><a href="javascript:moveProd('+prod['prod_id']+')"><span class="icon_p">P</span>'+prod['prod_name']+'</a></p>'+
					 '		</div>'+
					 '		<div class="evalue"><em class="star"></em>'+prodInfo['avg_rate'].toFixed(1)+'<i>('+prodInfo['rating_count']+')</i></div>'+
					 '		<div class="data"><strong>리뷰</strong>'+prodInfo['review_count']+'<span>|</span><strong>찜하기</strong>'+prodInfo['pick_count']+'</div>';
			if(!isCateg){
				li = li + '	<div class="cate">'+prodInfo['categ_disp']+'</div>';	
			}
			li = li +'	</div>'+
					 '</li>';
			return li;
		} else if(type == VIEW_FLAT_TYPE){
			let li = '<li>'+
					 '	<div class="list_ea_flat">';
			if(isCateg){
				li = li + ' <div class="btn_select" onclik="alert(\'dd\')"><input type="checkbox" class="s_checkbox" id="compare_'+prod['prod_id']+'" onclick="pushCompareTab(this,'+idx+');"><label for="compare_'+prod['prod_id']+'"><em></em></label></div>';
			}
			li = li +'		<div class="title">'+
					 '			<p class="ellipsis_1"><a href="javascript:moveProd('+prod['prod_id']+')"><span class="icon_p">P</span>'+prod['prod_name']+'</a></p>'+
					 '		</div>'+
					 '		<div class="img_desc" onclick="moveProd('+prod['prod_id']+')">'+
					 '			<div class="img">'+
					 '				<img src="'+imgSrc+'" class="img_type_product">'+
					 '			</div>'+
					 '			<div class="desc ellipsis_3">'+feature+
					 '			</div>'+
					 '		</div>'+
					 '		<div class="btn_like"><input onclick="pickContentGlobal('+cInfoType+', '+prod['prod_id']+', this)" type="checkbox" class="l_checkbox" name="like_cb" data="like_'+cbPrefix+'_'+prod['prod_id']+'" id="'+cbId+'" '+myPickedChed+'><label for="'+cbId+'"><em></em></label></div>'+
					 '		<div class="evalue"><span class="rate_star '+rateImg+'"></span>'+prodInfo['avg_rate'].toFixed(1)+'<i>('+prodInfo['rating_count']+')</i></div>'+
					 '		<div class="data"><strong>조회</strong>'+prodInfo['prod']['view_count']+'<span>|</span><strong>리뷰</strong>'+prodInfo['review_count']+'<span>|</span><strong>한마디</strong>'+prodInfo['comm_count']+'<span>|</span><strong>'+msgBtnPick+'</strong>'+prodInfo['pick_count']+'</div>';					 
			if(!isCateg){
				li = li + '	<div class="cate">'+prodInfo['categ_disp']+'</div>';	
			}	 
			li = li +'	</div>'+
					 '</li>';
			return li;
		}
	}
	
	
	function makeExtReviewLi(reviewInfo, type, isCateg){
		
		let review = reviewInfo;
		
		let imgSrc = prefix+"/image/img_general_product_default.png";

		if(review['image_path'] != null) {
			imgSrc = imgPrefix +'/'+ review['image_path']+"_300.png";
		} 
		
		let regDate = "-";
		if(review['reg_date'] != null){
			regDate = moment(review['reg_date']).format('YYYY.MM.DD');
		}
				
		let playUi = '<img src="'+prefix+'/image/img_play.png" class="btn_play" alt="play">';
		let thumbUi = '<img src="'+imgSrc+'" class="img_type_review">';
		
		let mallTag;
		if(review['mall_name'] == '네이버') {
			mallTag = '<div class="where"><em class="wh naver">NAVER</em><i></i></div>';
		} else if(review['mall_name'] == '다음') {
			mallTag = '<div class="where"><em class="wh daum">DAUM</em><i></i></div>';
		} else if(review['mall_name'] == '유투브') {
			thumbUi = playUi + thumbUi;
			mallTag = '<div class="where"><em class="wh youtube">Youtube</em><i></i></div>';
		} else {
			mallTag = '<div class="where"><em class="wh etc">ETC</em><i></i></div>';
		}
		
		let myPickedChed = review['my_pick_count'] == 0 ? '' : 'checked';
		let cInfoType = review['content_info_type'];
		let cbPrefix = 'rext';
		let cbId = randomString();
		
		//log("myPickedChed Ext ["+review['rel_id']+"]" + myPickedChed);
		
		
		if(type == VIEW_CARD_TYPE){
		
			let li = '<li>'+
				 '	<div class="list_ea_card">'+
				 '		<div class="btn_like"><input onclick="pickContentGlobal('+cInfoType+', '+review['rel_id']+', this)" type="checkbox" class="l_checkbox" name="like_cb" data="like_'+cbPrefix+'_'+review['rel_id']+'" id="'+cbId+'" '+myPickedChed+'><label for="'+cbId+'"><em></em></label></div>'+
				 '		<div class="thumb_wrap" onclick="moveExtReview(\''+review['rel_id']+'\')">'+
				 '			<div class="thumb">'+
				 				thumbUi +
				 '			</div>'+
				 '		</div>'+
				 '		<div class="title">'+
				 '			<p class="ellipsis_2"><a href="javascript:moveExtReview(\''+review['rel_id']+'\')"><span class="icon_r">R</span>'+review['title']+'</a></p>'+
				 '		</div>'+
				 mallTag +
				 '		<div class="data">'+regDate+'</span></div>';
			if(!isCateg){
				li = li + '	<div class="cate">'+review['categ_disp']+'</div>';	
			}								 
			li = li +'	</div>'+
				 '</li>';
			return li;
		} else if(type == VIEW_FLAT_TYPE){
			let li = '<li>'+
				 '	<div class="list_ea_flat">'+
				 '		<div class="title">'+
				 '			<p class="ellipsis_1"><a href="javascript:moveExtReview(\''+review['rel_id']+'\')"><span class="icon_r">R</span>'+review['title']+'</a></p>'+
				 '		</div>'+
				 '		<div class="img_desc" onclick="moveExtReview(\''+review['rel_id']+'\')">'+
				 '			<div class="img">'+
				 				thumbUi +
				 '			</div>'+
				 '			<div class="desc ellipsis_3">'+review['preview']+
				 '			</div>'+
				 '		</div>'+
				 '		<div class="btn_like"><input onclick="pickContentGlobal('+cInfoType+', '+review['rel_id']+', this)" type="checkbox" class="l_checkbox" name="like_cb" data="like_'+cbPrefix+'_'+review['rel_id']+'" id="'+cbId+'" '+myPickedChed+'><label for="'+cbId+'"><em></em></label></div>'+
				 mallTag +
				 '		<div class="data">'+regDate+'<span>|</span><strong>'+msgBtnPick+'</strong>'+review['pick_count']+'</div>';
			if(!isCateg){
				li = li + '	<div class="cate">'+review['categ_disp']+'</div>';	
			}		 
			li = li +'	</div>'+
				 '</li>';
			return li;
		} else {}
	}
	
	function makeNoThumbContentLi(contentInfo, isCateg){
			
		let content = contentInfo;
		let mallTag;
		
		if(content['mall_name'] == '네이버') {
			mallTag = '<div class="where" onclick="moveExtReview(\''+content['rel_id']+'\')"><em class="wh naver">NAVER</em><i></i></div>';
		} else if(content['mall_name'] == '다음') {
			mallTag = '<div class="where" onclick="moveExtReview(\''+content['rel_id']+'\')"><em class="wh daum">DAUM</em><i></i></div>';
		} else {
			mallTag = '<div class="where" onclick="moveExtReview(\''+content['rel_id']+'\')"><em class="wh etc">ETC</em><i></i></div>';
		}		
		
		let myPickedChed = content['my_pick_count'] == 0 ? '' : 'checked';
		let cInfoType = content['content_info_type'];
		let cbPrefix = 'rext';
		let cbId = randomString();
		
		let li = '<li>' +
				 '	<div class="list_ea_flat_noimg">' +
				 '		<div class="title" onclick="moveExtReview(\''+content['rel_id']+'\')">' +
				 '			<p class="ellipsis_1"><a href="">' + content['title'] + '</a></p>' +
				 '		</div>' +
				 '		<div class="img_desc" onclick="moveExtReview(\''+content['rel_id']+'\')">' +
				 '			<div class="desc ellipsis_2">' +
				 '				' + content['preview'] +
				 '			</div>' +
				 '		</div>' +
				 		mallTag +
				 '		<div class="data">'+moment(content['write_date']).format('YYYY.MM.DD')+'</div>' +
				 '		<div class="btn_like"><input onclick="pickContentGlobal('+cInfoType+', '+content['rel_id']+', this)" type="checkbox" class="l_checkbox" name="like_cb" data="like_'+cbPrefix+'_'+content['rel_id']+'" id="'+cbId+'" '+myPickedChed+'><label for="'+cbId+'"><em></em></label></div>' +
				 '	</div>' +
				 '</li>';
		
		return li;		
		
	}
	
	/**
	 * 
	 * @param list		컨텐츠 리스트
	 * @param viewType	카드형(0), 플랫형(1)
	 * @param isCateg	카테고리별 컨텐츠 조회시 사용(true, false) - 카테고리별 컨텐츠 조회시 true
	 * @param idx		제품 비교 선택에 사용
	 * @returns
	 */
	function makeContentLiList(list, viewType, isCateg, idx) {
		
		let resList = "";	
		
		for(var i=0; i<list.length; i++) {
			
			let contentType = list[i]['content_info_type'];
			
			if(contentType == TYPE_REVIEW) {
				resList = resList + makeReviewLi(list[i], viewType, isCateg);
			} else if(contentType == TYPE_PROD) {
				resList = resList + makeProdLi(list[i], viewType, isCateg, idx+i);
			} else if(contentType == TYPE_REVIEW_EXT_VIDEO) {
				resList = resList + makeExtReviewLi(list[i], viewType, isCateg);
			} else if(contentType == TYPE_REVIEW_EXT_ARTICLE) {
				resList = resList + makeExtReviewLi(list[i], viewType, isCateg);
			} /*else if(contentType == TYPE_REVIEW_EXT_NO_THUMB) {
				resList = resList + makeNoThumbContentLi(list[i], isCateg);
			} */else {}
			
		}
		
		return resList;
	}
	
	function makeCommentLi(comm){
		let regDate = moment(comm['reg_date']);
		let profileImage = prefix + '/image/img_general_profile_default.png';
		if(comm['profile_image_path'] != null) {
			profileImage = imgPrefix + "/" + comm['profile_image_path'] + "_300.png";
		}
		let content = comm['contents'].replace(/(\n|\r\n)/g, '<br>');
		
		
		let appendLi = ''+
			'<li id="li_comment_'+comm['comment_id']+'">'+
	        '    <div class="list_type_product_land">'+
	        '        <div class="profile"><img src="'+profileImage+'" alt=""></div>'+
	        '        <div class="text" >'+
	        '            <p class="title ellipsis_1">'+comm['nick_name']+'</p>'+
	        '            <p class="date">'+regDate.format('YYYY.MM.DD')+'</p>'+
	        '        </div>'+
	        '    </div>'+
	        '    <div class="desc menty">'+content+'</div>';
		if(comm['my_comment'] == true){
			appendLi += '<div class="prd_line" onclick="delComment('+comm['comment_id']+')"><p style="text-align: center;">삭제</p></div>';
		}
        	appendLi += '</li>';
        	
		
        return appendLi;
	}
	
	function makeCommentList(commList){
		let appendLi = '';
		for(let i=0; i<commList.length; i++){
			appendLi += makeCommentLi(commList[i]);
		}
		
		return appendLi;
	}
	
	
	
	
	
	function contentInfoListToLi(cInfoList) {
		if(cInfoList == null || cInfoList.length==0){return '';}
		
		let liList = '';
		for(let i=0; i<cInfoList.length; i++) {
			let cInfo = cInfoList[i];
			
			
			let appendLi = '';
			if(cInfo['content_info_type'] == TYPE_PROD) {
			
				let prodLink = prefix + '/m/prod/product.do?prod_id=' +cInfo['prod_id'] ;
				
				appendLi = '' +
				'	<li>'+
			    '        <div class="list_ea_flat">'+
			    '		 <a href="'+prodLink+'">' +
			    '            <div class="title">'+
			    '                <p class="ellipsis_1">'+cInfo['disp_prod_name']+'</p>'+
			    '            </div>'+
			    '            <div class="img_desc">'+
			    '                <div class="img">'+
			    '                    <img src="'+cInfo['represent_img_url']+'" class="img_type_product">'+
			    '                </div>'+
			    '                <div class="desc ellipsis_4">'+
			    					cInfo['feature_dp_string']+
			    '                </div>'+
			    '            </div>'+
			    '            <div class="evalue"><span class="rate_star rate_star_'+cInfo['rating_to_star_img_cls']+'"></span>'+cInfo['rating_avg_as_string']+'<i>('+cInfo['rating_count_as_string']+')</i>'+			    
			    '			<i>MRP('+cInfo['mrp']+')</i>' + 
			    ' 			</div>'+
			    
			    '		</a>'+
			    '            <div class="data1">'+
			    '            	<strong>조회</strong>'+cInfo['view_count_as_string']+'<span>|</span>'+
			    '            	<a href="'+prodLink+'#conbox1'+'"><strong>리뷰</strong>'+cInfo['review_count_as_string']+'</a><span>|</span>'+
			    '            	<a href="'+prodLink+'#conbox3'+'"><strong>한마디</strong>'+cInfo['comment_count_as_string']+'</a><span>|</span>'+
			    '            	<strong>공유</strong>'+cInfo['comment_count_as_string']+'<span>|</span>'+
			    '            	<strong>좋아요</strong>'+cInfo['like_count_as_string']+''+
			    '           </div>'+
			    '        </div>'+
			    '    </li>';
			    
			    
			} else if(cInfo['content_info_type'] == TYPE_REVIEW_EXT_VIDEO) {
				
				let prodLink   = prefix + '/m/prod/product.do?prod_id=' +cInfo['prod_id'] ;
				let reviewLink = prefix + '/m/review/ext.do?rel_id=' +cInfo['review_id'] ;
					
				if(cInfo['represent_img_url'] != null) {
					
				
					appendLi = '' +
					'	<li>'+
		            '        <div class="list_ea_flat">'+
		            '		<a href="'+reviewLink+'">'+
		            '            <div class="img_desc desc_youtube">'+
		            '                <div class="img">'+
		            '                    <img src="'+prefix+'/image/img_play.png" class="btn_play" alt="play">'+
		            '                    <img src="'+cInfo['represent_img_url']+'" class="img_type_review">'+
		            '                </div>'+
		            '                <div class="desc ellipsis_3">'+
		            					cInfo['title']+
		            '                </div>'+
		            '            </div>'+
		            '            <div class="where"><em class="wh '+cInfo['mall_to_icon_name']+'"></em><p class="data">'+cInfo['nick_name']+'<span>|</span>'+cInfo['reg_date_as_string']+'</p><i></i></div>'+
		            '            <div class="data1"><strong>조회</strong>'+cInfo['view_count_as_string']+'<span>|</span><strong>좋아요</strong>'+cInfo['pick_count_as_string']+'</div>'+
		            '		</a>'+
		            '            <div class="rel_product"><em>P</em><a href="'+prodLink+'" class="ellipsis_1">'+cInfo['disp_prod_name']+'</a><span class="eval"><i></i>'+cInfo['rating_avg_as_string']+'</span></div>'+
		            '        </div>'+
		            '    </li>';
		            
				} else{
					appendLi = '' +
					'	<li id="ext_'+cInfo['review_id']+'">'+
		            '        <div class="list_ea_flat">'+
		            '		<a href="'+reviewLink+'">'+
		            '            <div class="img_desc desc_youtube">'+
		            '                <div class="img">'+
		            '                    <img src="'+prefix+'/image/img_play.png" class="btn_play" alt="play">'+
		            //'                    <img src="'+cInfo['youtube_image_url']+'" class="img_type_review">'+
		            '                    <img id="ext_'+cInfo['review_id']+'_youtube_img" src="" class="img_type_review">'+
		            '                </div>'+
		            '                <div class="desc ellipsis_3">'+
		            					cInfo['title']+
		            '                </div>'+
		            '            </div>'+
		            '            <div class="where"><em class="wh '+cInfo['mall_to_icon_name']+'"></em><p class="data">'+cInfo['nick_name']+'<span>|</span>'+cInfo['reg_date_as_string']+'</p><i></i></div>'+
		            '            <div class="data1"><strong>조회</strong>'+cInfo['view_count_as_string']+'<span>|</span><strong>좋아요</strong>'+cInfo['pick_count_as_string']+'</div>'+
		            '		</a>'+
		            '            <div class="rel_product"><em>P</em><a href="'+prodLink+'" class="ellipsis_1">'+cInfo['disp_prod_name']+'</a><span class="eval"><i></i>'+cInfo['rating_avg_as_string']+'</span></div>'+
		            '        </div>'+
		            '    </li>';
				/*
					appendLi = '' +
					'	<li>'+
		            '        <div class="list_ea_flat_noimg">'+
		            '		<a href="'+reviewLink+'">'+
		            '            <div class="title">'+
		            '                <p class="ellipsis_1">'+cInfo['title']+'</p>'+
		            '            </div>'+
		            '            <div class="img_desc">'+
		            '                <div class="desc ellipsis_4">'+
										cInfo['preview']+
		            '                </div>'+
		            '            </div>'+
		            '            <div class="where"><em class="wh '+cInfo['mall_to_icon_name']+'"></em><p class="data">'+cInfo['nick_name']+'<span>|</span>'+cInfo['reg_date_as_string']+'</p><i></i></div>'+
		            '            <div class="data1"><strong>조회</strong>'+cInfo['view_count_as_string']+'<span>|</span><strong>좋아요</strong>'+cInfo['pick_count_as_string']+'</div>'+
		            '		</a>'+
		            '            <div class="rel_product"><em>P</em><a href="'+prodLink+'" class="ellipsis_1">'+cInfo['disp_prod_name']+'</a><span class="eval"><i></i>'+cInfo['rating_avg_as_string']+'</span></div>'+
		            '        </div>'+
		            '    </li>';
		        */
				}
				
				
			} else if(cInfo['content_info_type'] == TYPE_REVIEW || cInfo['content_info_type'] == TYPE_REVIEW_EXT_ARTICLE) {
				
				let prodLink   = prefix + '/m/prod/product.do?prod_id=' +cInfo['prod_id'] ;
				let reviewLink = prefix + '/m/review/review.do?review_id=' +cInfo['review_id'] ;
				
				if(cInfo['represent_img_url'] != null) {
				
					let addLine = '';
					if(cInfo['content_info_type'] == TYPE_REVIEW) {
						addLine = '<strong>도움</strong>'+cInfo['like_count_as_string']+'<span>|</span>';
					} else {
						reviewLink = prefix + '/m/review/ext.do?rel_id=' +cInfo['review_id'] ;
					}
					
					
					
					appendLi = '' +
					'	<li>'+
		            '        <div class="list_ea_flat">'+
		            '	<a href="'+reviewLink+'">'+
		            
		            '            <div class="title">'+
		            '                <p class="ellipsis_1">'+cInfo['title']+'</p>'+
		            '            </div>'+
		            '            <div class="img_desc">'+
		            '                <div class="img">'+
		            '                    <img src="'+cInfo['represent_img_url']+'" class="img_type_review">'+
		            '                </div>'+
		            '                <div class="desc ellipsis_4">'+
		            cInfo['preview'] +
		            '                </div>'+
		            '            </div>'+
		            '            <div class="where"><em class="wh '+cInfo['mall_to_icon_name']+'"></em><p class="data">'+cInfo['nick_name']+'<span>|</span>'+cInfo['reg_date_as_string']+'</p><i></i></div>'+
					'            <div class="data1">'+
					'            	<strong>조회</strong>'+cInfo['view_count_as_string']+'<span>|</span>'+
					addLine+
					'            	<strong>좋아요</strong>'+cInfo['pick_count_as_string']+'</div>'+
					
					'	</a>'+
					
					'            <div class="rel_product"><em>P</em><a href="'+prodLink+'" class="ellipsis_1">'+cInfo['disp_prod_name']+'</a><span class="eval"><i></i>'+cInfo['rating_avg_as_string']+'</span></div>'+
		            '        </div>'+
		            '    </li>';
		            
		            
				} else {
				
					if(cInfo['content_info_type'] == TYPE_REVIEW) {	
					} else {
						reviewLink = prefix + '/m/review/ext.do?rel_id=' +cInfo['review_id'] ;
					}
				
				
					appendLi = '' +
					'	<li>'+
		            '        <div class="list_ea_flat_noimg">'+
		            '		<a href="'+reviewLink+'">' + 
		            '            <div class="title">'+
		            '                <p class="ellipsis_1">'+cInfo['title']+'</p>'+
		            '            </div>'+
		            '            <div class="img_desc">'+
		            '                <div class="desc ellipsis_4">'+
										cInfo['preview']+
		            '                </div>'+
		            '            </div>'+
		            '            <div class="where"><em class="wh '+cInfo['mall_to_icon_name']+'"></em><p class="data">'+cInfo['nick_name']+'<span>|</span>'+cInfo['reg_date_as_string']+'</p><i></i></div>'+
		            '            <div class="data1"><strong>조회</strong>'+cInfo['view_count_as_string']+'<span>|</span><strong>좋아요</strong>'+cInfo['pick_count_as_string']+'</div>'+
		            '		</a>'+
		            '            <div class="rel_product"><em>P</em><a href="'+prodLink+'" class="ellipsis_1">'+cInfo['disp_prod_name']+'</a><span class="eval"><i></i>'+cInfo['rating_avg_as_string']+'</span></div>'+
		            '        </div>'+
		            '    </li>';
				}
			}
			
			liList = liList + appendLi;
		}
		
		return liList;
	}