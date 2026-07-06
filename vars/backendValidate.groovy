def call(String directory = 'server') {
    dir(directory) {
        if (isUnix()) {
            sh '''
                node -e "require('./package.json'); console.log('package.json is valid')"

                if [ -f server.js ]; then
                    node --check server.js
                elif [ -f index.js ]; then
                    node --check index.js
                fi
            '''
        } else {
            bat '''
                node -e "require('./package.json'); console.log('package.json is valid')"

                if exist server.js (
                    node --check server.js
                ) else (
                    if exist index.js (
                        node --check index.js
                    )
                )
            '''
        }
    }
}
