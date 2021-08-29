//IIFE
(function () {
    const div_table = document.getElementById('div_table');
    div_table.innerHTML = '';

    const grid = new gridjs.Grid({
        columns: [
            {id: 'name', name: '이름'},
            {id: 'owner', name: '소유주'},
            {id: 'species', name: '종'},
            {id: 'sex', name: '성별'},
            {id: 'birth', name: '생일'},
        ],
        server: {
            url: CONTEXT_PATH + '/pets?size=10',
            data: (opts) => {
                return axios.get(opts.url)
                    .then(function (response) {
                        // handle success
                        console.log(response);
                        return {
                            data: response.data.list,
                            total: response.data.count
                        };

                    })
                    .catch(function (error) {
                        // handle error
                        console.log(error);
                    });
            }
        },
        pagination: {
            server: {
                url: (prev, page, size) => `${prev}&page=${page}`
            }
        },
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
    }).render(div_table);

})();
