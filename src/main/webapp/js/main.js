$(document).ready(function(){
	$('.list-group-item').on('click', function(e) {
		// if ($(e.target).attr('class') == 'list-group-item') {
		$(e.target).parent().find('.active').removeClass('active');
		$(e.target).addClass('active');
		console.log($(e.target).text());
	});
});