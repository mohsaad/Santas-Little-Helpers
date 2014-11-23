$(document).ready(function(){
	$('.list-group-item').on('click', function(e)
	{
		$(e.target).parent().find('.active').removeClass('active');
		$(e.target).addClass('active');

		console.log($(e.target).text());
	});
});