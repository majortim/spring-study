
//IIFE
(function () {
    const grid = new gridjs.Grid({
      columns: [
        { id : 'name', name : '이름'},
        { id : 'owner', name : '소유주'},
        { id : 'species', name : '종'},
        { id : 'sex', name : '성별'},
        { id : 'birth', name : '생일'},
      ],
      server: {
        url: CONTEXT_PATH + '/list',
        data: (opts) => {
            console.log(opts);
        /*
                const resp = JSON.parse(this.response);
                  // make sure the output conforms to StorageResponse format:
                  // https://github.com/grid-js/gridjs/blob/master/src/storage/storage.ts#L21-L24
                  resolve({
                    data: resp.data.map(card => [card.name, card.lang, card.released_at, card.artist]),
                    total: resp.total_cards,
                  });
        */
        }
      },
      pagination: {
        limit: 5
      }
    });

    const grid2 = new gridjs.Grid({
        columns: [
            { id : 'name', name : '이름'},
            { id : 'owner', name : '소유주'},
            { id : 'species', name : '종'},
            { id : 'sex', name : '성별'},
            { id : 'birth', name : '생일'},
        ],
        server: {
            url: CONTEXT_PATH + '/list',
            data: (opts) => {
                return new Promise((resolve, reject) => {
                    // let's implement our own HTTP client

                    /*
                    const xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function() {
                        if (this.readyState === 4) {
                            if (this.status === 200) {
                                const resp = JSON.parse(this.response);

                                // make sure the output conforms to StorageResponse format:
                                // https://github.com/grid-js/gridjs/blob/master/src/storage/storage.ts#L21-L24
                                resolve({
                                   // data: resp.data.map(card => [card.name, card.lang, card.released_at, card.artist]),
                                    //total: resp.total_cards,
                                });
                            } else {
                                reject();
                            }
                        }
                    };
                    xhttp.open("GET", opts.url, true);
                    xhttp.send();
                    */
                });
            }
        },
        pagination: {
            limit: 5
        }
    });

    axios.get( CONTEXT_PATH + '/list')
      .then(function (response) {
        // handle success
        // console.log(response);

        const div_table = document.getElementById('div_table');
        div_table.innerHTML = '';

        /*
        const grid = new gridjs.Grid({
          columns: [
            { id : 'name', name : '이름'},
            { id : 'owner', name : '소유주'},
            { id : 'species', name : '종'},
            { id : 'sex', name : '성별'},
            { id : 'birth', name : '생일'},
          ],
          data: response.data,
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
              showing: 'Showing',
              of: 'of',
              to: 'to',
              results: '결과',
            },
            loading: '로딩...',
            noRecordsFound: '조회 결과가 없습니다',
            error: '데이터를 가져오는 중 오류가 발생했습니다',
          }
        }).render(div_table);
        */



      })
      .catch(function (error) {
        // handle error
        console.log(error);
      })
      .then(function () {
        // always executed
      });
})();
