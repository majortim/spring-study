//IIFE
(function () {
    const div_table = document.getElementById('div_table');
    div_table.innerHTML = '';

    const server_config = {
        url: CONTEXT_PATH + '/pets',
        data: (opts) => {
            return axios.get(opts.url)
                .then(function (response) {
                    // handle success
                    // console.log(response);
                    return {
                        data: response.data.list,
                        total: response.data.count
                    };

                })
                .catch(function (error) {
                    // handle error
                    // console.log(error);
                });
        }
    }

    const pagination_config = {
        limit: 10,
        server: {
            url: (prev, page, limit) => {
                return `${prev}?size=${limit}&page=${page}`
            }
        }
    }

    const grid = new gridjs.Grid({
        columns: [
            {id: 'id', name: 'Id', hidden: true},
            {id: 'name', name: '이름'},
            {id: 'owner', name: '소유주'},
            {id: 'species', name: '종'},
            {id: 'sex', name: '성별'},
            {id: 'birth', name: '생일'},
            {
                id: 'action',
                name: '수정/삭제',
                formatter: (_, row) => gridjs.html(
                    `
                    <span>
                        <button type="button" class="button_edit" value="${row.cells[0].data}">수정</button>
                        <button type="button" class="button_delete" value="${row.cells[0].data}">삭제</button>
                    </span>
                    `)
            }
        ],
        server: server_config,
        pagination: pagination_config,
        language: {
            search: {
                placeholder: '검색어를 입력하세요...',
            },
            sort: {
                sortAsc: '오름차순으로 정렬합니다',
                sortDesc: '내림차순으로 정렬합니다',
            },
            pagination: {
                previous: '<',
                next: '>',
                navigate: (page, pages) => `${page} / ${pages} 페이지`,
                page: (page) => `${page} 페이지`,
                showing: ' ',
                of: '/',
                to: '-',
                results: ' ',
            },
            loading: '로딩...',
            noRecordsFound: '조회 결과가 없습니다',
            error: '데이터를 가져오는 중 오류가 발생했습니다',
        }
    })
    .on('ready',
        () => {
            div_table.querySelectorAll('.button_edit')
                .forEach((element) => element.addEventListener('click', (evt) => {
                location.href = CONTEXT_PATH + '/pets/' + evt.target.value + '/edit';
            }));

            div_table.querySelectorAll('.button_delete')
                .forEach((element) => element.addEventListener('click', (evt) => {
                if(confirm("해당 항목을 삭제하시겠습니까?")){
                    const formDelete = document.querySelector('#div_delete_form form');

                    formDelete.action = CONTEXT_PATH + '/pets/' + evt.target.value + '/delete';
                    formDelete.submit();
                }
            }));

        })
    .render(div_table);
    console.log(grid);
})();

