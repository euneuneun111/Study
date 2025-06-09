process.on('exit', () => {
    console.log('프로세스가 종료되었습니다.');
});

process.on('uncaughtException', () => {
    console.log('예외가 발생했습니다.');
});

error.error.error();